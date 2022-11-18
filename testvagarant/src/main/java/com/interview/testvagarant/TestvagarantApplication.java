package com.interview.testvagarant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.naming.directory.InvalidAttributesException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class TestvagarantApplication {

	public static ArrayList<Team> teams = new ArrayList<Team>();

	static {
		teams.add(new Team("GT", 20, Arrays.asList(true, true, false, false, true)));
		teams.add(new Team("LSG", 18, Arrays.asList(true, false, false, true, true)));
		teams.add(new Team("RR", 16, Arrays.asList(true, false, true, false, false)));
		teams.add(new Team("DC", 14, Arrays.asList(true, true, false, true, false)));
		teams.add(new Team("RCB", 14, Arrays.asList(false, true, true, false, false)));
		teams.add(new Team("KKR", 12, Arrays.asList(false, true, true, false, true)));
		teams.add(new Team("PBKS", 12, Arrays.asList(false, true, false, true, false)));
		teams.add(new Team("SRH", 12, Arrays.asList(true, false, false, false, false)));
		teams.add(new Team("CSK", 8, Arrays.asList(false, false, true, false, true)));
		teams.add(new Team("MI", 6, Arrays.asList(false, true, false, true, true)));
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Team {
		private String teamName;
		private Integer points;
		private List<Boolean> results;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestvagarantApplication.class, args);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("The team results are : ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Teams\t:\tPoints\t:\tLast Five Matches");
		System.out.println("-----------------------------------------------------------------------");
		teams.forEach(e -> {
			System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
			e.getResults().forEach(r -> System.out.print(r + "\t"));
			System.out.println();
		});
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("-----Sample Output 1 --------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("The two Consecutively losing teams are ");
		int avg = 0;
		List<Team> twoConsecutiveLosingTeam = getConsecutiveWinnigOrLosingTeam(false, 2);
		System.out.println("Teams\t:\tPoints\t:\tLast Five Matches");
		System.out.println("-----------------------------------------------------------------------");
		for (Team e : twoConsecutiveLosingTeam) {
			System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
			e.getResults().forEach(r -> System.out.print(r + "\t"));
			System.out.println();
			avg += e.getPoints();
		}
		System.out.println("The average points are : " + avg / twoConsecutiveLosingTeam.size());
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("-----Sample Output 2 --------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("The Three Consecutively winning teams are ");
		avg = 0;
		List<Team> twoConsecutiveWinnigTeam = getConsecutiveWinnigOrLosingTeam(true, 2);
		for (Team e : twoConsecutiveWinnigTeam) {
			System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
			e.getResults().forEach(r -> System.out.print(r + "\t"));
			System.out.println();
			avg += e.getPoints();
		}
		System.out.println("The average points are : " + avg / twoConsecutiveWinnigTeam.size());

		// dynamic output
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("-----Dynamic Output  --------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------");
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the number of match count ot be considered as consecutive [ 1 <= N <=5 ]:");
			Integer count = Integer.parseInt(scanner.nextLine());
			if (count <= 0 || count > 5) {
				throw new InvalidAttributesException("number of win/loss count cannot be validated");
			}
			System.out.println("Enter the match result [Can be win or lose]");
			String result = scanner.nextLine().toUpperCase();

			System.out.println("The " + count + " Consecutively " + result + "ing teams are ");
			avg = 0;
			if (!result.equalsIgnoreCase("WIN") || !result.equalsIgnoreCase("LOSE")) {
				throw new InvalidAttributesException("number of win/loss count cannot be validated");
			}
			List<Team> consecutive = getConsecutiveWinnigOrLosingTeam(result.equalsIgnoreCase("WIN"), count);
			for (Team e : consecutive) {
				System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
				e.getResults().forEach(r -> System.out.print(r + "\t"));
				System.out.println();
				avg += e.getPoints();
			}
			System.out.println("The average points are : " + avg / consecutive.size());
		} catch (Exception e) {
			System.err.println("Please Enter Proper values :" + e.getMessage());
		}
	}

	// for N consecutive or wins
	public static List<Team> getConsecutiveWinnigOrLosingTeam(Boolean result, Integer number) {
		List<Team> resultantTeams = new LinkedList();
		for (Team t : teams) {
			Object[] array = t.getResults().toArray();
			Boolean isAdded = false;
			Integer similar = number;
			for (int i = 0; i < array.length - number; i++) {
				if (isAdded == false) {
					if ((Boolean) array[i] == result) {
						for (int j = 1; j <= number; j++) {
							if ((Boolean) array[i] == result && (Boolean) array[i + j] == result
									&& (Boolean) array[i] == (Boolean) array[i + j]) {
								similar--;
							}
						}
						if (similar == 0) {
							resultantTeams.add(t);
							isAdded = true;
						}
					}
				}
			}
		}
		return resultantTeams;
	}

}
