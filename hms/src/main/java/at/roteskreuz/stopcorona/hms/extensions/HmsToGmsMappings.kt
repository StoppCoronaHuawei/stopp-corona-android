package at.roteskreuz.stopcorona.hms.extensions

import com.google.android.gms.nearby.exposurenotification.ExposureConfiguration
import com.google.android.gms.nearby.exposurenotification.ExposureInformation
import com.google.android.gms.nearby.exposurenotification.ExposureInformation.ExposureInformationBuilder
import com.google.android.gms.nearby.exposurenotification.ExposureSummary
import com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey
import com.huawei.hms.contactshield.ContactDetail
import com.huawei.hms.contactshield.ContactSketch
import com.huawei.hms.contactshield.DiagnosisConfiguration
import com.huawei.hms.contactshield.PeriodicKey

fun PeriodicKey.toTemporaryExposureKey(): TemporaryExposureKey {

    return TemporaryExposureKey.TemporaryExposureKeyBuilder()
        .setKeyData(getContent())
        .setRollingStartIntervalNumber(getPeriodicKeyValidTime().toInt())
        .setRollingPeriod(getPeriodicKeyLifeTime().toInt())
        .setTransmissionRiskLevel(getInitialRiskLevel())
        .build();

}

fun ContactSketch.toExposureSummary(): ExposureSummary {

    return ExposureSummary.ExposureSummaryBuilder()
        .setDaysSinceLastExposure(getDaysSinceLastHit())
        .setMatchedKeyCount(getNumberOfHits())
        .setMaximumRiskScore(getMaxRiskValue())
        .setSummationRiskScore(getSummationRiskValue())
        .setAttenuationDurations(getAttenuationDurations())
        .build();

}

fun ContactDetail.toExposureInformation(): ExposureInformation {

    return ExposureInformationBuilder()
        .setDateMillisSinceEpoch(getDayNumber())
        .setAttenuationValue(getAttenuationRiskValue())
        .setTransmissionRiskLevel(getInitialRiskLevel())
        .setDurationMinutes(getDurationMinutes())
        .setAttenuationDurations(getAttenuationDurations())
        .setTotalRiskScore(getTotalRiskValue())
        .build()
}

fun ExposureConfiguration.toDiagnosisConfiguration(): DiagnosisConfiguration {

    val test: ExposureConfiguration = ExposureConfiguration.ExposureConfigurationBuilder()
        .build()


    val something = test.minimumRiskScore

    return DiagnosisConfiguration.Builder()
        .setMinimumRiskValueThreshold(minimumRiskScore)
        .setAttenuationRiskValues(*attenuationScores)
        .setDaysAfterContactedRiskValues(*daysSinceLastExposureScores)
        .setDurationRiskValues(*durationScores)
        .setInitialRiskLevelRiskValues(*transmissionRiskScores)
        .setAttenuationDurationThresholds(*durationAtAttenuationThresholds)
        .build()

}