package me.cqc.pattern.ChainofResponsibility;

import me.cqc.pattern.ChainofResponsibility.approver.DepartmentManager;
import me.cqc.pattern.ChainofResponsibility.approver.GeneralManager;
import me.cqc.pattern.ChainofResponsibility.approver.TeamLeader;

public class Client {
    public static void main(String[] args) {

        // 构建责任链：组长 → 部门经理 → 总经理
        Approver generalManager = new GeneralManager(null); // 总经理是最后一个审批者，无下家
        Approver deptManager = new DepartmentManager(generalManager);
        Approver teamLeader = new TeamLeader(deptManager);

        teamLeader.approve(1);
        teamLeader.approve(3);
        teamLeader.approve(5);
        teamLeader.approve(7);
        teamLeader.approve(90);
        teamLeader.approve(0);
    }
}
