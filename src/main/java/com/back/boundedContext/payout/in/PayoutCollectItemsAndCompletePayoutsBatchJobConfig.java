package com.back.boundedContext.payout.in;

import com.back.boundedContext.payout.app.PayoutFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 배치 설정 파일
@Slf4j
@Configuration
public class PayoutCollectItemsAndCompletePayoutsBatchJobConfig {
    private static final int CHUNK_SIZE = 10;

    private final PayoutFacade payoutFacade;

    public PayoutCollectItemsAndCompletePayoutsBatchJobConfig(PayoutFacade payoutFacade) {
        this.payoutFacade = payoutFacade;
    }
    // 정산 아이템 모으기 및 정산 수행
    @Bean
    public Job payoutCollectItemsAndCompletePayoutsJob(
            JobRepository jobRepository,
            Step payoutCollectItemsStep,
            Step payoutCompletePayouts
    ) {
        // JobBuilder - Job을 만듬
        // Job안에 Stop이 들어가며 Job 수행
        // Step 객체 순서 지정 start - next
        return new JobBuilder("payoutCollectItemsAndCompletePayoutsJob", jobRepository)
                .start(payoutCollectItemsStep)
                .next(payoutCompletePayouts)
                .build();
    }

    // Step1 배치 : 정산 후보로부터 -> 정산 아이템 모으기
    @Bean
    public Step payoutCollectItemsStep(JobRepository jobRepository) {
        return new StepBuilder("payoutCollectItemsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int processedCount = payoutFacade.collectPayoutItemsMore(CHUNK_SIZE).getData();

                    if (processedCount == 0) {
                        return RepeatStatus.FINISHED;
                    }

                    contribution.incrementWriteCount(processedCount);

                    return RepeatStatus.CONTINUABLE;
                })
                .build();
    }
    // Step 2 배치 : 정산 수행
    @Bean
    public Step payoutCompletePayouts(JobRepository jobRepository) {
        return new StepBuilder("payoutCompletePayouts", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    int processedCount = payoutFacade.completePayoutsMore(CHUNK_SIZE).getData();

                    if (processedCount == 0) {
                        return RepeatStatus.FINISHED;
                    }

                    contribution.incrementWriteCount(processedCount);

                    return RepeatStatus.CONTINUABLE;
                })
                .build();
    }
}