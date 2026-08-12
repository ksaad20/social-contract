# Social Contract MVP
# Custom ProGuard/R8 rules will be added only when required.

# Keep Room database entities and generated database code.
-keep class androidx.room.** { *; }

# Keep Kotlin serialization/reflection metadata where applicable.
-keepattributes *Annotation*
-keepattributes Signature
