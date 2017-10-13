import java.util.*;
import java.io.*;

public class MemoryScheduler {

	static ArrayList<MemoryBlock>  MemoryList;
	static ArrayList<Job>  JobList;

	static void FirstFit() {




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
			tempJob.waitingTime= 0;
		}
		
	}


}

class MemoryBlock {
	int memoryNumber;
	int space;
	ArrayList<Job> jobQueue;

	public MemoryBlock() {
		jobQueue = new ArrayList<Job>();
	}
}

class Job {
	int jobNumber;
	int remainingTime;
	int jobSize;
	int waitingTime;

	public Job() {
	}
}