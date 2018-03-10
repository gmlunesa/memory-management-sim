package memorymanagementsim; 


import java.util.*;
import java.io.*;

public class MemoryManager {

	static ArrayList<MemoryBlock> MemoryList;
	static ArrayList<Job> JobList;

	public static void placement(int policy) {

		// timer loop
		// for (int timerCtr = 0; !areAllJobsAllocated(); timerCtr++) {
			

			// System.out.println("TIME: " + timerCtr + "\n---------------------------------------");
			// printProgress();

			// System.out.println("throughput: " + getThroughput() + "; waiting quueue length : " + getWaitingQueueLength());

			for (int memItr = 0; memItr < MemoryList.size(); memItr++) {

				if (!MemoryList.get(memItr).jobQueue.isEmpty()) {			// if the memory has been allocated to a job

					int jobIdx = MemoryList.get(memItr).jobQueue.get(0);

					if (JobList.get(jobIdx).remainingTime == 0) {	// if the remaining time is zero
						JobList.get(jobIdx).status = 2;				// set status to 2, which stands for compeleted		
						MemoryList.get(memItr).jobQueue.remove(0);	// remove the allocated job from the memory
						MemoryList.get(memItr).internalFragmentation = -1;
					}
				}
			}

			// sort MemoryList by the MemoryNumber (since this is first fit)

			// sortBlocks(policy);

			// traverse per job in the joblist
			// routine to allocate the unallocated jobs
			for (int joblistItr = 0; joblistItr < JobList.size(); joblistItr++) {


				if (JobList.get(joblistItr).status == 0) {
					boolean isLarger = true;

					// traverse through the MemoryList
					for (int memItr = 0; memItr < MemoryList.size(); memItr++) {
						 
						if (MemoryList.get(memItr).jobQueue.isEmpty() 	&&		// if current memory block is empty &&
							JobList.get(joblistItr).status == 0 		&&		// jobstatus == 0 or unallocated 
							MemoryList.get(memItr).space >= JobList.get(joblistItr).jobSize) {	// current memory block size is greater than the job size

							JobList.get(joblistItr).status = 1;
							// JobList.get(joblistItr).remainingTime--;
							MemoryList.get(memItr).jobQueue.add(joblistItr);
							MemoryList.get(memItr).internalFragmentation = MemoryList.get(memItr).space - JobList.get(joblistItr).jobSize;
							
							break;

						}

						if (MemoryList.get(memItr).space >= JobList.get(joblistItr).jobSize) {
							isLarger = false;
						}
					}

					// if (isLarger == true) {
					// 	// System.out.println("heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" + joblistItr);
					// 	JobList.get(joblistItr).status = 3;
					// }
				}

				if (JobList.get(joblistItr).status == 0 || JobList.get(joblistItr).status == 3) {
					JobList.get(joblistItr).waitingTime++;
				}
			}

			// traverse per block in the memory list
			// routine to decrement the remaining time of the jobs that have been allocated
			for (int memItr = 0; memItr < MemoryList.size(); memItr++) {

				if (!MemoryList.get(memItr).jobQueue.isEmpty()) {			// if the memory has been allocated to a job

					int jobIdx = MemoryList.get(memItr).jobQueue.get(0);
					System.out.println("JOB " + jobIdx + " : " + JobList.get(jobIdx).remainingTime + " s");
					JobList.get(jobIdx).remainingTime--;					// decrement the remaining amount of time

					// if (JobList.get(jobIdx).remainingTime == 0) {	// if the remaining time is zero
					// 	JobList.get(jobIdx).status = 2;				// set status to 2, which stands for compeleted		
					// 	MemoryList.get(memItr).jobQueue.remove(0);	// remove the allocated job from the memory
					// 	MemoryList.get(memItr).internalFragmentation = -1;
					// }
				}
			}
		// } // end timer loop
		// printProgress();
	}

	public static void sortBlocks(int policy) {
		if (policy == 1) {
			Collections.sort(MemoryList, new Comparator<MemoryBlock>(){
			    public int compare(MemoryBlock mem1, MemoryBlock mem2) {
			        return mem1.memoryNumber - mem2.memoryNumber;
			    }
			});
		} else if (policy == 2) {
			Collections.sort(MemoryList, new Comparator<MemoryBlock>(){
			    public int compare(MemoryBlock mem1, MemoryBlock mem2) {
			        return mem1.space - mem2.space;
			    }
			});
		} else if (policy == 3) {
			Collections.sort(MemoryList, new Comparator<MemoryBlock>(){
			    public int compare(MemoryBlock mem1, MemoryBlock mem2) {
			        return mem2.space - mem1.space;
			    }
			});

		}
	}


	public static boolean areAllJobsAllocated() {
		for (int i = 0; i < JobList.size(); i++) {
			if (JobList.get(i).status == 0 || JobList.get(i).status == 1) {
				return false;
			}
		}
		return true;
	}

	protected static int getWaitingQueueLength(ArrayList<Job> JobList) {
		int waitingQueueLength = 0;

		for (int i = 0; i < JobList.size(); i++) {
			if (JobList.get(i).status == 0 || JobList.get(i).status == 3) {
				waitingQueueLength++;
			}
		}

		return waitingQueueLength;
	}

	protected static int getThroughput(ArrayList<Job> JobList) {
		int throughput = 0;
		for (int i = 0; i < JobList.size(); i++) {
			if (JobList.get(i).status == 2) {
				throughput++;
			} else if (JobList.get(i).status == 1 && JobList.get(i).remainingTime == 0) {
				throughput++;
			}
		}

		return throughput;

	}

	protected static void printProgress() {

		for (int i = 0; i < MemoryList.size(); i++) {
			if (!MemoryList.get(i).jobQueue.isEmpty()) {
				System.out.println("MEMORY " + MemoryList.get(i).memoryNumber + " : " + (MemoryList.get(i).jobQueue.get(0) + 1)  + " " + JobList.get(MemoryList.get(i).jobQueue.get(0)).status);
			} else {
				System.out.println("MEMORY " + (i+1) + " : " + "Empty");
			}
		}

		System.out.println("-----------------------------------");

		for (int i = 0; i < JobList.size(); i++) {
			System.out.print("JOB " + i + " : " + JobList.get(i).status + " " + JobList.get(i).remainingTime + " " + JobList.get(i).waitingTime);
			System.out.println();
		}

	}

	protected static double getPercentage(ArrayList<MemoryBlock> MemoryList, ArrayList<Job> JobList) {
		double sumOfOccupied = 0;
		double totalSpace = 0;

		for (int i = 0; i < MemoryList.size(); i++) {

			totalSpace = (double) totalSpace + MemoryList.get(i).space;
			if (!MemoryList.get(i).jobQueue.isEmpty()) {
				sumOfOccupied = (double) sumOfOccupied + JobList.get(MemoryList.get(i).jobQueue.get(0)).jobSize;
			}
		}


		double percent = (double) ((sumOfOccupied / totalSpace) * 100);
		System.out.println(((sumOfOccupied / totalSpace) * 100) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!----------------------------------------------");
		return percent;


	}


	public static boolean isLarger(int jobSize, int maxMemory) {
		
		for (int i = 0; i < MemoryList.size(); i++) {
			if (maxMemory < jobSize) {
				System.out.println(MemoryList.get(i).space + " vs " + jobSize);
				return true;
			}
		}
		return false;
	}

	public static void initializeData() {
		MemoryList = new ArrayList<MemoryBlock>();
		JobList = new ArrayList<Job>();
		int[] memorysize = {9500, 7000, 4500, 8500, 3000, 9000, 1000, 5500, 1500, 500};
		int maxMemory = 9500;

		for (int i = 0; i < memorysize.length; i++) {
			MemoryBlock tempMemBlock = new MemoryBlock();
			tempMemBlock.memoryNumber = i+1; 
			tempMemBlock.space = memorysize[i];
			MemoryList.add(tempMemBlock);
		}

		int[] jobtime = {5, 4, 8, 2, 2, 6, 8, 10, 7, 6, 5, 8, 9, 10, 10, 7, 3, 1, 9, 3, 7, 2, 8, 5, 10};
		int[] jobsize = {5760, 4190, 3290, 2030, 2550, 6990, 8940, 740, 3930, 6890, 6580, 3820, 9140, 420, 220, 7540, 3210, 1380, 9850, 3610, 7540, 2710, 8390, 5950, 760};

		for (int i = 0; i < jobtime.length; i++) {
			Job tempJob = new Job();
			tempJob.jobNumber = i+1;
			tempJob.remainingTime = jobtime[i];
			tempJob.jobSize = jobsize[i];
			tempJob.waitingTime = 0;


			if (isLarger(jobsize[i], maxMemory)) {
				tempJob.status = 3;
			} else {
				tempJob.status = 0;
			}

			JobList.add(tempJob);
		}
	}


	// public static void main(String[] args) {

		
	// 	FirstFit();
	// }
}

class MemoryBlock {
	int memoryNumber;
	int space;
	int timesUsed;
	int internalFragmentation;
	ArrayList<Integer> jobQueue;

	public MemoryBlock() {
		jobQueue = new ArrayList<Integer>();
		timesUsed = 0;
		internalFragmentation = -1;
	}
}

class Job {
	int jobNumber;
	int remainingTime;
	int jobSize;
	int waitingTime;
	int status;

	public Job() {
		waitingTime = 0;
		status = 0;

		// Status guide
		// status = 0 // if unallocated
		// status = 1 // if being allocated
		// status = 2 // if already finished
	}
}