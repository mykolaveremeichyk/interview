package com.interview;

import com.interview.LoveMachine.Love;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.interview.LoveMachine.LikesCount;
import static org.assertj.core.api.Assertions.assertThat;

public class LoveMachineTest {

    private static final List<Love> loves = Arrays.asList(LoveMachine.loves);

    @Test
    public void countTop3DoctorsByLikes_ShouldReturnExpectedResult_GivenInputListContainsDuplicates() {

        List<LikesCount> expectedResult = List.of(
                new LikesCount("euice@yahoo.com", 7),
                new LikesCount("nogin@att.net", 3),
                new LikesCount("alhajj@sbcglobal.net", 3));

        List<LikesCount> top3doctorsByLikes = LoveMachine.countTop3DoctorsByLikes(loves);

        assertThat(top3doctorsByLikes)
                .isNotEmpty()
                .hasSize(3)
                .isEqualTo(expectedResult);
    }

    @Test
    public void countTop3DoctorsByLikes_ShouldReturnExpectedResult_GivenInputListDoesNotContainDuplicates() {

        List<LikesCount> expectedResult = List.of(
                new LikesCount("euice@yahoo.com", 4),
                new LikesCount("nogin@att.net", 2),
                new LikesCount("brbarret@gmail.com", 2));

        assertThat(loves.size()).isEqualTo(24);
        List<Love> deduplicatedLikes = LoveMachine.deduplicatePatientToDoctorLikes(loves);
        assertThat(deduplicatedLikes.size()).isEqualTo(15);

        List<LikesCount> top3doctorsByLikes = LoveMachine.countTop3DoctorsByLikes(deduplicatedLikes);

        assertThat(top3doctorsByLikes)
                .isNotEmpty()
                .hasSize(3)
                .isEqualTo(expectedResult);
    }

    @Test
    public void countTop3PatientsByLikes_ShouldReturnExpectedResult_GivenInputListContainsDuplicates() {

        List<LikesCount> expectedResult = List.of(
                new LikesCount("drjlaw@verizon.net", 4),
                new LikesCount("mccurley@sbcglobal.net", 3),
                new LikesCount("shazow@icloud.com", 3));

        List<LikesCount> top3PatientsByLikesCount = LoveMachine.countTop3PatientsByLikes(loves);

        assertThat(top3PatientsByLikesCount)
                .isNotEmpty()
                .hasSize(3)
                .isEqualTo(expectedResult);
    }

    @Test
    public void countTop3PatientsByLikes_ShouldReturnExpectedResult_GivenInputListDoesNotContainDuplicates() {

        List<LikesCount> expectedResult = List.of(
                new LikesCount("mccurley@sbcglobal.net", 2),
                new LikesCount("drjlaw@verizon.net", 2),
                new LikesCount("jdhildeb@msn.com", 1));

        assertThat(loves.size()).isEqualTo(24);
        List<Love> deduplicatedLikes = LoveMachine.deduplicatePatientToDoctorLikes(loves);
        assertThat(deduplicatedLikes.size()).isEqualTo(15);

        List<LikesCount> top3PatientsByLikesCount = LoveMachine.countTop3PatientsByLikes(deduplicatedLikes);

        assertThat(top3PatientsByLikesCount)
                .isNotEmpty()
                .hasSize(3)
                .isEqualTo(expectedResult);
    }

}
