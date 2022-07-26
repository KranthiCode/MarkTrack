package com.kks.fintrack.beans;

import java.util.HashMap;
import java.util.Map;

public class ProgressDetails {
  private String task;
  private int total = 0;
  private int totalProcessed = 0;
  private ProgressStatus status;
  private String finishedAt;
  private String startedAt;
  private String lastUpdatedAt;
  private long totalTimeTaken;

  public enum ProgressStatus {
    IN_PROGRESS, COMPLETED, FAILED;
  }

  public static Map<String, ProgressDetails> taskProgressHash = new HashMap<>();

  @Override
  public String toString() {
    return "ProgressDetails [finishedAt=" + finishedAt + ", lastUpdatedAt=" + lastUpdatedAt + ", startedAt=" + startedAt
        + ", status=" + status + ", task=" + task + ", total=" + total + ", totalProcessed=" + totalProcessed
        + ", totalTimeTaken=" + totalTimeTaken + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((finishedAt == null) ? 0 : finishedAt.hashCode());
    result = prime * result + ((lastUpdatedAt == null) ? 0 : lastUpdatedAt.hashCode());
    result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((task == null) ? 0 : task.hashCode());
    result = prime * result + total;
    result = prime * result + totalProcessed;
    result = prime * result + (int) (totalTimeTaken ^ (totalTimeTaken >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProgressDetails other = (ProgressDetails) obj;
    if (finishedAt == null) {
      if (other.finishedAt != null)
        return false;
    } else if (!finishedAt.equals(other.finishedAt))
      return false;
    if (lastUpdatedAt == null) {
      if (other.lastUpdatedAt != null)
        return false;
    } else if (!lastUpdatedAt.equals(other.lastUpdatedAt))
      return false;
    if (startedAt == null) {
      if (other.startedAt != null)
        return false;
    } else if (!startedAt.equals(other.startedAt))
      return false;
    if (status != other.status)
      return false;
    if (task == null) {
      if (other.task != null)
        return false;
    } else if (!task.equals(other.task))
      return false;
    if (total != other.total)
      return false;
    if (totalProcessed != other.totalProcessed)
      return false;
    if (totalTimeTaken != other.totalTimeTaken)
      return false;
    return true;
  }

  /**
   * @return String return the task
   */
  public String getTask() {
    return task;
  }

  /**
   * @param task the task to set
   */
  public void setTask(String task) {
    this.task = task;
  }

  /**
   * @return int return the total
   */
  public int getTotal() {
    return total;
  }

  /**
   * @param total the total to set
   */
  public void setTotal(int total) {
    this.total = total;
  }

  /**
   * @return int return the totalProcessed
   */
  public int getTotalProcessed() {
    return totalProcessed;
  }

  /**
   * @param totalProcessed the totalProcessed to set
   */
  public void setTotalProcessed(int totalProcessed) {
    this.totalProcessed = totalProcessed;
  }

  /**
   * @return ProgressStatus return the status
   */
  public ProgressStatus getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(ProgressStatus status) {
    this.status = status;
  }

  /**
   * @return String return the finishedAt
   */
  public String getFinishedAt() {
    return finishedAt;
  }

  /**
   * @param finishedAt the finishedAt to set
   */
  public void setFinishedAt(String finishedAt) {
    this.finishedAt = finishedAt;
  }

  /**
   * @return String return the startedAt
   */
  public String getStartedAt() {
    return startedAt;
  }

  /**
   * @param startedAt the startedAt to set
   */
  public void setStartedAt(String startedAt) {
    this.startedAt = startedAt;
  }

  /**
   * @return String return the lastUpdatedAt
   */
  public String getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  /**
   * @param lastUpdatedAt the lastUpdatedAt to set
   */
  public void setLastUpdatedAt(String lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }

  /**
   * @return int return the totalTimeTaken
   */
  public long getTotalTimeTaken() {
    return totalTimeTaken;
  }

  /**
   * @param totalTimeTaken the totalTimeTaken to set
   */
  public void setTotalTimeTaken(long totalTimeTaken) {
    this.totalTimeTaken = totalTimeTaken;
  }

}
