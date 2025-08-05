package me.cqc.pattern.ChainofResponsibility.approver;

import me.cqc.pattern.ChainofResponsibility.Approver;

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
