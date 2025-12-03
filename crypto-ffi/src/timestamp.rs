//! Custom timestamp type for UniFFI that maps to kotlinx.datetime.Instant in Kotlin.

#[cfg(not(target_family = "wasm"))]
use std::time::{Duration, SystemTime};

/// A timestamp type that maps to `kotlinx.datetime.Instant` in Kotlin bindings.
///
/// This is a newtype wrapper around `SystemTime` that allows us to configure
/// UniFFI to generate Kotlin code using `kotlinx.datetime.Instant` instead of
/// `java.time.Instant`.
#[cfg(not(target_family = "wasm"))]
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub struct KotlinInstant(pub SystemTime);

#[cfg(not(target_family = "wasm"))]
impl KotlinInstant {
    /// Create a new KotlinInstant from a SystemTime
    pub fn new(time: SystemTime) -> Self {
        Self(time)
    }

    /// Create a KotlinInstant from Unix epoch seconds
    pub fn from_epoch_secs(secs: u64) -> Self {
        Self(SystemTime::UNIX_EPOCH + Duration::from_secs(secs))
    }

    /// Get the inner SystemTime
    pub fn into_inner(self) -> SystemTime {
        self.0
    }
}

#[cfg(not(target_family = "wasm"))]
impl From<SystemTime> for KotlinInstant {
    fn from(time: SystemTime) -> Self {
        Self(time)
    }
}

#[cfg(not(target_family = "wasm"))]
impl From<KotlinInstant> for SystemTime {
    fn from(instant: KotlinInstant) -> Self {
        instant.0
    }
}

// UniFFI custom type declaration
// The builtin type is SystemTime (which maps to Timestamp in UniFFI)
// The custom_type! macro generates the necessary converter trait implementation
#[cfg(not(target_family = "wasm"))]
uniffi::custom_type!(KotlinInstant, SystemTime);
