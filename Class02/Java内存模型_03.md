# 同步原语的内存语义

## volatile

> 对volatile变量的单个读写，相当于使用同一个锁对这些单个读写操作作了同步。
>
> 1. volatile写和锁的释放有相同的内存语义；
> 2. volatile读和锁的获取有相同的内存语义。

### volatile变量的两大特性

可见性：对一个变量的读之前，总是能看到任意线程对这个volatile变量最后的写入。

原子性：对任意单个volatile变量的读/写具有原子性。但是volatile++这种复合操作不具有原子性。[为什么volatile++不是原子性的？](https://blog.csdn.net/dm_vincent/article/details/79604716)

### volatile写读的内存语义

1. 当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值刷新到主内存。
2. 当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效。线程接下来从主内存中读取共享变量。

### volatile内存语义的实现

1. 当第二个操作是volatile写时，不管第一个操作是什么，都不能重排序。
2. 当第一个操作是volatile读时，不管第二个操作是什么，都不能重排序。
3. 当第一个操作是volatile读，当第二个操作是volatile写时，不能重排序。