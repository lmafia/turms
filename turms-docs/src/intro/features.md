# 特性

Turms基于读扩散消息模型进行架构设计，对业务数据变化感知同时支持推模式、拉模式与推拉模式（详细文档：[Turms业务数据变化感知](https://turms-im.github.io/docs/for-developers/status-aware.html)），其他大部分的设计细节也源自商用即时通讯项目。并且相比很多技术栈落后的开源项目或闭源商用项目，Turms解决方案也是全球即时通讯开源领域内唯一一个基于现代化架构与现代化工程技术，并且适合中大规模部署的解决方案。

另外，架构设计是权衡的艺术，部分IM产品以功能丰富为口号，但功能丰富的代价就是只适用于小体量的用户规模（如企业内部通讯）。而Turms以极限性能为第一要义，同时支持完整的（而非丰富的）IM业务功能，以支持中大规模即时通讯场景。具体原因可查阅[Turms集合设计](https://turms-im.github.io/docs/for-developers/schema.html)以及[Turms可观测性体系](https://turms-im.github.io/docs/for-developers/observability.html)相关文档。

当您需要将Turms与其他开源IM项目做具体特性的比对时，您可以先照着Turms下述的特性与其他开源IM项目进行比对。通常情况下，您能通过这样的比对，发现专业IM项目与业余IM项目之间的区别。另外，在`产品对比`章节下，我们也提到了Turms项目的缺点供您参考。

注意：当前Turms项目的主要缺点是不对直播/聊天室业务场景提供支持。直播/聊天室业务场景的技术实现并不难，但其产品需求、质量属性要求与约束性条件与一般的社交场景存在着较大差异，故Turms第一版设计不对其提供支持；另外，Turms也不太适用于小规模的企业通讯场景，用Turms往企业通讯场景上套就有点“杀鸡用牛刀”，因为企业通讯更强调功能丰富而非极限性能，与Turms的目标不符，所以二者的上层设计也不同。如果希望支持企业通讯场景，您还需要对Turms进行二次开发。

### 业务功能相关特性

1. （业务功能完善性）Turms支持几乎所有商用即时通讯产品所支持的[即时通讯相关功能](https://turms-im.github.io/docs/features/)（甚至还有更多的业务功能），且无业务功能限制。
   （数据分析与挖掘功能会在之后发布turms-data的时候提供，具体细节可查阅 [Turms数据分析](https://turms-im.github.io/docs/for-developers/data-analytics.html)）
2. （功能拓展性）Turms同时支持两种拓展模式：配置参数与开发插件。当然您也完全可以对源码进行修改。目前用于接入的MinIO对象存储服务的插件turms-plugin-minio就是基于turms-plugin实现的。
3. （配置灵活性）Turms提供了上百个配置参数供用户定制，以满足各种需求。并且大部分配置都可以在集群运作时（不需要停机），进行集群级别的同步更新，并且无性能损失。

### 通用架构特性

1. （敏捷性）支持在用户无感知的情况下，对Turms服务端进行停机更新，为快速迭代提供可能
2. （可伸缩性）无状态架构，Turms集群支持弹性扩展与异地多活的部署实现，用户可通过DNS就近接入
3. （可部署性）支持容器化部署，方便与云服务对接，以实现全自动化部署与运维
4. （可观测性）具备相对完善的可观测性体系设计，为业务统计与错误排查提供可能
5. （可拓展性）能同时支持中大型即时通讯场景，即便用户体量由小变大也无需重构（当然，对于大型运用场景还有很多优化的工作需要做，但当前架构不影响后期的无痛升级）
6. （安全性）提供限流防刷机制与用户/IP黑名单机制，以抵御大部分CC攻击
7. （简单性）核心架构“轻量”，方便学习与二次开发（原因请查阅 [Turms架构设计](https://turms-im.github.io/docs/for-developers/architecture.html)）
8. Turms使用MongoDB分片架构，以支持请求路由（如读写分离），同时也支持跨地域多活部署与数据主主同步，为大规模跨国部署提供实际操作的可能

### 其他特性

1. 重视可观测性体系建设（详细文档：[Turms可观测性体系](https://turms-im.github.io/docs/for-developers/observability.html)）。具体而言包括以下三个维度：

   * 日志（针对事件）：共提供了三大类日志：监控日志、业务日志、统计日志
   * 度量（针对可聚合数据）。包括实时的系统运行状态信息，以及实时的业务数据
   * 链路追踪

   补充：Turms服务端自身会在实现高效的前提下尽可能提供更多监控数据，但不提供一些尽管常见但对性能影响较大，且更适合第三方服务实现的功能（如：日活）。对于这类拓展功能，您可以通过对Turms的日志与度量数据进行离线或实时分析来实现该类功能。

2. 运作极为高效。

   Turms服务端在所有业务流程的代码实现上，都对性能有着极致追求，具体请查阅代码实现。

   - 网络
     - I/O：Turms服务端基于响应式编程，Turms服务端的所有网络请求在底层都是基于Netty的异步无阻塞模型实现的（包括数据库调用、Redis调用、服务发现注册、RPC等）。因此Turms服务端在各个功能模块上都能充分利用硬件资源（而传统服务端不能）
     - 编码：Turms服务端与Turms客户端间的通信数据采用Protobuf编码；Turms服务端之间的RPC请求与响应均采用定制化的二进制编码，以保证极致的高效。
   - 线程
     - Turms服务端具有优秀的线程模型，其线程数恒定，与在线用户规模以及请求数无关。由于Turms接入层默认线程数与CPU数一致，因此Turms服务端能充分利用CPU缓存，并相比传统服务端，极大地减少了线程上下文切换开销
     - 业务逻辑处理过程中，无同步加锁操作，只有CAS操作
   - 内存
     - 在划分内存空间时，合理且充分地循环利用堆内存与直接内存
     - 如果您在JVM配置中添加了`-XX:+AlwaysPreTouch`，即可保证Turms在服务端启动时向系统commit所有需要的堆内存，保证Turms服务端在运作时不会发生缺页异常，以提升运行效率
     - Turms通过重写MongoDB/Redis客户端依赖的部分实现，保证了Turms服务端中无冗余的内存分配，极大地提高了内存的有效使用率
   - 缓存：Turms服务端各功能模块充分利用本地内存缓存