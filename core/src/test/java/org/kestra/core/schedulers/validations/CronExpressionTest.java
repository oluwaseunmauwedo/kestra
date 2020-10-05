package org.kestra.core.schedulers.validations;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.kestra.core.models.triggers.types.Schedule;
import org.kestra.core.models.validations.ModelValidator;
import org.kestra.core.utils.IdUtils;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@MicronautTest
class CronExpressionTest {
    @Inject
    private ModelValidator modelValidator;

    @Test
    void CronValidation() throws Exception {
        Schedule build = Schedule.builder()
            .id(IdUtils.create())
            .type(Schedule.class.getName())
            .cron("* * * * *")
            .build();

        assertThat(modelValidator.isValid(build).isEmpty(), is(true));

        build = Schedule.builder()
            .type(Schedule.class.getName())
            .cron("$ome Inv@lid Cr0n")
            .build();

        assertThat(modelValidator.isValid(build).isPresent(), is(true));
        assertThat(modelValidator.isValid(build).get().getMessage(), containsString("invalid cron expression"));
    }
}
