# GarbageCollector load example

## Example app

Build and run:

```bash
mvn clean package
java -XX:+UseParallelGC -XX:MaxNewSize=16M -Xms64m -Xmx64m \
-XX:MaxTenuringThreshold=15 -XX:CompressedClassSpaceSize=64m \
-verbose:gc -XX:+PrintGCTimeStamps -XX:+PrintGCDetails \
-jar target/LiczSlowa-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Launch VisualVM, with Visual GC plugin to see Garbage Collector work.
Set refresh rate in Visual GC window to 100 ms.

### Parameters

- `-XX:+UseParallelGC` - selects Parallel Garbage Collector to be used
- `MaxNewSize` - max size of Eden Space
- `Xms` and `Xmx` - min and max Heap size
- `MaxTenuringThreshold` - max number of times for objects to be copied between S0 and S1
- `CompressedClassSpaceSize` - Meta Space size

## About GC in Java

Two phases - Mark and Sweep.

**Mark** - identify used and not used memory parts.
Used objects - main object(s), objects used by it, and so on.

**Sweep** - remove all not used parts.

JVM stops app threads for the duration of GC.

## GC implementations

- Serial Garbage Collector (`-XX:+UseSerialGC`) - single thread
- Parallel Garbage Collector (`-XX:+UseParallelGC`) - multi-thread, default for Java 7 and 8
- CMS (Concurrent Mark Sweep) Garbage Collector (`-XX:+UseParNewGC`) - shorter but more frequent garbage collection pauses
- G1 (Garbage First) Garbage Collector (`-XX:+UseG1GC`) - default for Java 9 and beyond

Others - like Epsilon, a No-Op Garbage Collector for
performance testing or extremely short lived jobs.

## G1 Garbage Collector

G1 partitions the heap into a set of equal-sized heap regions.
Each partition may be used as Eden, S0, S1 and Old (they purpose changes in time).

In firstly firstly clears regions most likely to be empty.
This results in good-enough memory retrieval - not all regions that could be emptied
are emptied in a single GC run, but it gives better performance (shorter app pause).

## Garbage Collector spaces

- Eden Space - for all new objects.
- Survivor Space - for objects that survive GC in Eden. Two Survivor Spaces, S0 and S1,
from which one is always empty. Not cleared data is copied from one to another during GC.
- Old / Tenured Space - for objects copied multiple times between S0 and S1.
- Meta Space - for class definitions loaded by class loaders - static data.

Eden + Survivor + Old = Heap Memory
