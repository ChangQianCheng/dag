package me.cqc.pattern.ChainofResponsibility.approver;

import me.cqc.pattern.ChainofResponsibility.Approver;

public class GeneralManager extends Approver {


    public GeneralManager(Approver nextApprover) {
        super(nextApprover);
    }

    @Override
    public void approve(int days) {
        if (days >= 8 ) {
            System.out.println("总经理审批通过：" + days + "天假期");
        } else if (nextApprover != null) {
            nextApprover.approve(days);
        } else {
            System.out.println("无人能审批：" + days + "天假期");
        }
    }
}
