package io.github.mathter.morph.data

/**
 * Java-friendly variant of [[PathMap]] intended for Java interoperability.
 *
 * A `JPathMap` exposes the same semantics as `PathMap` but indicates that the
 * implementation is suitable for Java callers (for example, it may expose
 * mutation semantics expected by Java or provide Java collection views).
 *
 * Implementations SHOULD document the following contract details:
 *  - Mutability: whether the returned `JPathMap` is mutable from Java and how
 *    modifications are propagated to any backing Scala view.
 *  - Backing semantics: whether the Java view is backed by the original
 *    `PathMap` (so changes reflect in both directions) or is a defensive copy.
 *  - Null handling: how `null` values/keys are represented and whether `null`
 *    is permitted.
 *
 * Convenience factory and conversion methods are available on [[PathMap]]:
 *  - `PathMap.jempty` to create an empty Java-style map
 *  - `PathMap.jempty` and `PathMap.asJava`/`asScala` to convert between views
 *
 * Use this trait as the return type for APIs that must interoperate cleanly
 * with Java code.
 */
trait JPathMap extends PathMap

