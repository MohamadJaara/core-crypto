package com.wire.crypto.model

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import kotlin.time.toKotlinDuration
import com.wire.crypto.DeviceStatus
import com.wire.crypto.CredentialType
import com.wire.crypto.WirePolicy
import com.wire.crypto.X509Identity as UniffiX509Identity
import com.wire.crypto.WireIdentity as UniffiWireIdentity
import com.wire.crypto.CustomConfiguration as UniffiCustomConfiguration

/**
 * X509 certificate identity with Kotlin time types.
 */
data class X509Identity(
    /** user handle e.g. `john_wire` */
    val handle: String,
    /** Name as displayed in the messaging application e.g. `John Fitzgerald Kennedy` */
    val displayName: String,
    /** DNS domain for which this identity proof was generated e.g. `whitehouse.gov` */
    val domain: String,
    /** X509 certificate identifying this client in the MLS group ; PEM encoded */
    val certificate: String,
    /** X509 certificate serial number */
    val serialNumber: String,
    /** X509 certificate not before */
    val notBefore: Instant,
    /** X509 certificate not after */
    val notAfter: Instant
)

/** Convert from UniFFI type to wrapper type */
fun UniffiX509Identity.toModel() = X509Identity(
    handle = handle,
    displayName = displayName,
    domain = domain,
    certificate = certificate,
    serialNumber = serialNumber,
    notBefore = notBefore.toKotlinInstant(),
    notAfter = notAfter.toKotlinInstant()
)

/** Convert wrapper type to UniFFI type */
fun X509Identity.toUniFFI() = UniffiX509Identity(
    handle = handle,
    displayName = displayName,
    domain = domain,
    certificate = certificate,
    serialNumber = serialNumber,
    notBefore = notBefore.toJavaInstant(),
    notAfter = notAfter.toJavaInstant()
)

/**
 * Wire identity with Kotlin time types.
 */
data class WireIdentity(
    /** Unique client identifier e.g. `T4Coy4vdRzianwfOgXpn6A:6add501bacd1d90e@whitehouse.gov` */
    val clientId: String,
    /** Status of the Credential at the moment this object is created */
    val status: DeviceStatus,
    /** MLS thumbprint */
    val thumbprint: String,
    /** Indicates whether the credential is Basic or X509 */
    val credentialType: CredentialType,
    /** In case 'credential_type' is [CredentialType.X509] this is populated */
    val x509Identity: X509Identity?
)

/** Convert from UniFFI type to wrapper type */
fun UniffiWireIdentity.toModel() = WireIdentity(
    clientId = clientId,
    status = status,
    thumbprint = thumbprint,
    credentialType = credentialType,
    x509Identity = x509Identity?.toModel()
)

/** Convert wrapper type to UniFFI type */
fun WireIdentity.toUniFFI() = UniffiWireIdentity(
    clientId = clientId,
    status = status,
    thumbprint = thumbprint,
    credentialType = credentialType,
    x509Identity = x509Identity?.toUniFFI()
)

/**
 * Custom configuration with Kotlin time types.
 */
data class CustomConfiguration(
    /**
     * Duration after which we will automatically force a self-update commit
     * Note: This isn't currently implemented
     */
    val keyRotationSpan: Duration?,
    /** Defines if handshake messages are encrypted or not */
    val wirePolicy: WirePolicy?
)

/** Convert from UniFFI type to wrapper type */
fun UniffiCustomConfiguration.toModel() = CustomConfiguration(
    keyRotationSpan = keyRotationSpan?.toKotlinDuration(),
    wirePolicy = wirePolicy
)

/** Convert wrapper type to UniFFI type */
fun CustomConfiguration.toUniFFI() = UniffiCustomConfiguration(
    keyRotationSpan = keyRotationSpan?.toJavaDuration(),
    wirePolicy = wirePolicy
)
