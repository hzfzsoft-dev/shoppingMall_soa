package org.fzsoft.shoppingmall.utils.scheduletime;

import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Boyce 2016年9月10日 下午2:54:09
 */
public class TimeNextModel implements Comparable<TimeNextModel> {
    Nextable next;
    Date result;

    private String TIMEDTASK_TRIGGER_GROUP = "TIMEDTASK_TRIGGER_GROUP";

    CronTriggerImpl cronTrigger = null;

    public TimeNextModel(Nextable next) {
        this.next = next;
        cronTrigger = (CronTriggerImpl) TriggerBuilder.newTrigger().withIdentity(TIMEDTASK_TRIGGER_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(next.getTriggerTime())).build();
        result = cronTrigger.computeFirstFireTime(null);
    }

    public Date getNextTimeAndNext() {
        result = cronTrigger.getFireTimeAfter(result);
        return result;
    }

    public Date getNextTime() {
        return result;
    }

    public Nextable returnModel() {
        return next;
    }

    @Override
    public int compareTo(TimeNextModel o) {

        return result.after(o.result) ? 1 : -1;
    }

    public static void main(String[] args) {
        String time = "* 1 15 ? * 0";
        CronTrigger c = new CronTrigger(time, TimeZone.getDefault());
        SimpleTriggerContext t = new SimpleTriggerContext();
        t.update(new Date(), new Date(), new Date());
        Date result = c.nextExecutionTime(t);
        t.update(result, result, result);
        System.out.println(result);
    }
}
