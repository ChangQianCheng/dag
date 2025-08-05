package me.cqc.pattern.ChainofResponsibility;

public abstract  class Approver {
    protected Approver nextApprover;

    public Approver(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void approve(int  days) ;
}
