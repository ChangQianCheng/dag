package me.cqc.pattern.ChainofResponsibility.approver;

import me.cqc.pattern.ChainofResponsibility.Approver;

public class TeamLeader extends Approver {
    public TeamLeader(Approver nextApprover) {
        super(nextApprover);
    }

    @Override
    public void approve(int days) {
        if ( 0< days && days <= 3) {
            System.out.println("组长审批通过：" + days + "天假期");
        } else if (nextApprover != null) {
            nextApprover.approve(days);
        } else {
            System.out.println("无人能审批：" + days + "天假期");
        }
    }
}
