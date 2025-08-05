## 责任链模式（Chain of Responsibility Pattern）详解
### 一、什么是责任链模式？
责任链模式是一种行为型设计模式，其核心思想是：将多个请求处理者串联成一条链，当请求发出时，沿着这条链传递，直到有一个处理者能够处理该请求为止。
这种模式的本质是 “解耦请求发送者与接收者”，让请求的发送者无需知道具体由哪个接收者处理，同时允许动态调整处理者的顺序和组合。
### 二、解决的问题
在实际开发中，一个请求可能需要多个对象中的某一个来处理（例如：请假审批需要不同层级领导审批、日志系统需要不同级别处理器处理）。如果直接将请求与所有可能的处理者绑定，会导致：
发送者与处理者强耦合，新增处理者需修改发送者代码；
处理逻辑分散，难以维护和扩展。
责任链模式通过 “链式传递” 解决了这些问题，让请求自然流动到合适的处理者。
### 三、核心结构
责任链模式包含 3 个核心角色：
抽象处理者（Handler）：定义处理请求的接口，包含一个对 “下一个处理者” 的引用，以及处理请求的抽象方法。
具体处理者（ConcreteHandler）：实现抽象处理者的接口，负责处理自己职责范围内的请求；如果无法处理，则将请求传递给下一个处理者。
客户端（Client）：创建处理者对象并构建责任链，发起请求并传递给链的第一个处理者。
### 四、Java 代码示例
以 “员工请假审批流程” 为例：
请假 1-3 天：部门组长（TeamLeader）可审批；
请假 4-7 天：部门经理（DepartmentManager）可审批；
请假 8 天及以上：总经理（GeneralManager）可审批。
1. 抽象处理者（Approver）  
   定义审批接口，包含下一个审批者的引用和处理请假的抽象方法。  
```java
   // 抽象处理者：审批者
   public abstract class Approver {
   // 下一个审批者
   protected Approver nextApprover;

   // 构造方法：设置下一个审批者
   public Approver(Approver nextApprover) {
   this.nextApprover = nextApprover;
   }

   // 抽象方法：处理请假请求
   public abstract void approve(int days);
   }
```
2. 具体处理者（ConcreteHandler）  
   实现抽象处理者，处理各自职责范围内的请假请求，无法处理则传递给下一个审批者。  
```java
   // 具体处理者1：部门组长（处理1-3天请假）
   public class TeamLeader extends Approver {
   public TeamLeader(Approver nextApprover) {
   super(nextApprover);
   }

   @Override
   public void approve(int days) {
   if (days >= 1 && days <= 3) {
   System.out.println("部门组长审批通过：" + days + "天假期");
   } else if (nextApprover != null) {
   // 无法处理，传递给下一个审批者
   nextApprover.approve(days);
   } else {
   System.out.println("无人能审批：" + days + "天假期");
   }
   }
   }

// 具体处理者2：部门经理（处理4-7天请假）
public class DepartmentManager extends Approver {
public DepartmentManager(Approver nextApprover) {
super(nextApprover);
}

    @Override
    public void approve(int days) {
        if (days >= 4 && days <= 7) {
            System.out.println("部门经理审批通过：" + days + "天假期");
        } else if (nextApprover != null) {
            nextApprover.approve(days);
        } else {
            System.out.println("无人能审批：" + days + "天假期");
        }
    }
}

// 具体处理者3：总经理（处理8天及以上请假）
public class GeneralManager extends Approver {
public GeneralManager(Approver nextApprover) {
super(nextApprover);
}

    @Override
    public void approve(int days) {
        if (days >= 8) {
            System.out.println("总经理审批通过：" + days + "天假期");
        } else if (nextApprover != null) {
            nextApprover.approve(days);
        } else {
            System.out.println("无人能审批：" + days + "天假期");
        }
    }
}
```
3. 客户端（Client）  
   创建处理者对象，构建责任链（组长→经理→总经理），并发起不同天数的请假请求。  
```java
// 客户端代码
   public class Client {
   public static void main(String[] args) {
   // 构建责任链：组长 → 部门经理 → 总经理
   Approver generalManager = new GeneralManager(null); // 总经理是最后一个审批者，无下家
   Approver deptManager = new DepartmentManager(generalManager);
   Approver teamLeader = new TeamLeader(deptManager);

        // 发起不同天数的请假请求
        teamLeader.approve(2);   // 部门组长处理
        teamLeader.approve(5);   // 部门经理处理
        teamLeader.approve(10);  // 总经理处理
        teamLeader.approve(0);   // 无人处理（不在任何范围）
   }
   }
```
### 五、运行结果  
部门组长审批通过：2天假期  
部门经理审批通过：5天假期  
总经理审批通过：10天假期  
无人能审批：0天假期  

### 六、责任链的两种形式  
纯责任链：每个处理者要么完全处理请求，要么完全传递给下一个（如上述示例）；  
不纯的责任链：处理者可以部分处理请求后，再传递给下一个（例如：日志系统中，Debug 日志处理器记录后，Error 日志处理器继续处理）。  
### 七、优缺点  
优点：  
解耦请求发送者与处理者，发送者无需知道谁处理请求；  
动态调整责任链（增删处理者、修改顺序），符合 “开闭原则”；  
简化对象设计，每个处理者只需关注自己的职责。  
缺点：  
请求可能未被任何处理者处理（需考虑默认处理机制）；  
长链可能导致性能问题（请求需遍历多个处理者）。  
### 八、适用场景  
多个对象可能处理同一请求，但具体处理者不确定（如审批流程、权限校验）；  
需动态指定处理者或调整处理顺序（如日志级别过滤、过滤器链）；  
希望避免请求发送者与处理者直接耦合（如 Servlet 的 Filter 链、Spring 的 Interceptor）。  
责任链模式通过 “链式传递” 让请求自然找到合适的处理者，是构建灵活、可扩展系统的重要模式。  