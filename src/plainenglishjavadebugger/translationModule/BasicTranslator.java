package plainenglishjavadebugger.translationModule;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

/*
 * Scheduling a translation job: http://help.eclipse.org/kepler/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs.htm?cp=2_0_3_5
 * 
 * When the status of the debugger changes, the translation job can be started, joined, executed and terminated.
 * Can get other info about other jobs through IJobManager.
 * Set a listener to the debugger, when it's status changes, start the translation job.
 * 
 * Translation job should be a system job:
 * (http://help.eclipse.org/kepler/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs_progress.htm?cp=2_0_3_5_0)
 * 
 * This, most probably, is a SHORT job (Job Scheduling).
 */

public class BasicTranslator {

}
