import java.util.*;
import java.io.*;

public class MemoryManager {

	static ArrayList<MemoryBlock> MemoryList;
	static ArrayList<Job> JobList;
	static ArrayList<Job> FinishedJobList;

	static int throughPut = 0;


	static int getThroughput() {
		throughPut = FinishedJobList.size();
		return throughPut;

	}

	static void FirstFit() {

		// // timer loop
		// for (int timerCtr = 0; !JobList.isEmpty(); timerCtr++) {
		// 	// traverse through the joblist
		// 	for (int joblistItr = 0; joblistItr < JobList.size(); joblistItr++) {

		// 		// traverse through the MemoryList
		// 		for (int memItr = 0; memItr < MemoryList.size(); memItr++) {

		// 		}


		// 	}
		// }

		// timer loop
		for (int timerCtr = 1; !JobList.isEmpty(); timerCtr++) {

			// traverse per block in the memory list
			// routine to decrement the remaining time of the jobs that have been allocated
			for (int memItr = 0; memItr < MemoryList.size(); memItr++) {

				if (!MemoryList.get(memItr).jobQueue.isEmpty()) {				// if the memory has been allocated to a job
					MemoryList.get(memItr).jobQueue.get(0).remainingTime--;		// decremement the remaining time of the job
				}

				if (MemoryList.get(memItr).jobQueue.get(0).remainingTime == 0) {			// if the remaining time of the job the memory has been allocated to is now zero
					int joblistidx = MemoryList.get(memItr).jobQueue.get(0).jobNumber - 1;	// retrieve index of such job in the joblist
					JobList.get(joblistidx).status = 2; 									// set status of the job as finished (status = 2)
					MemoryList.get(memItr).jobQueue.remove(0);								// remove from the memory block's job queue
				}
			}

			//traverse per job in the joblist
			// routine to allocate the unallocated jobs
			for (int joblistItr = 0; joblistItr < JobList.size(); joblistItr++) {
				// traverse through the MemoryList
				for (int memItr = 0; memItr < MemoryList.size(); memItr++) {

				}
			}


		}

		



	}


	public static void main(String[] args) {
		MemoryList = new ArrayList<MemoryBlock>();
		int[] memorysize = {9500, 7000, 4500, 8500, 3000, 9000, 1000, 5500, 1500, 500};

		for (int i = 0; i < memorysize.length; i++) {
			MemoryBlock tempMemBlock = new MemoryBlock();
			tempMemBlock.memoryNumber = i+1;
			tempMemBlock.space = memorysize[i];
			MemoryList.add(tempMemBlock);
		}

		int[] jobtime = {5, 4, 8, 2, 2, 6, 8, 10, 7, 6};
		int[] jobsize = {5760, 4190, 3290, 2030, 2550, 6990, 8940, 740, 3930, 6890};

		for (int i = 0; i < jobtime.length; i++) {
			Job tempJob = new Job();
			tempJob.jobNumber = i+1;
			tempJob.remainingTime = jobtime[i];
			tempJob.jobSize = jobsize[i];
		}
		
	}


}

class MemoryBlock {
	int memoryNumber;
	int space;
	int timesUsed;
	ArrayList<Job> jobQueue;

	public MemoryBlock() {
		jobQueue = new ArrayList<Job>();
		timesUsed = 0;
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