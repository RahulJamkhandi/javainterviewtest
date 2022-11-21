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

	// Declaring a global data structure as static just to make the variable more
	// accessible without any referencing
	public static ArrayList<Team> teams = new ArrayList<Team>();

	static {
		/**
		 * As the data was already shared in a screenshot of 2019 IPL score board, I
		 * could take the liberty of hard coding the data.
		 * 
		 * Use of static block was planned in accordance to static block being loaded
		 * along with the global static object and populating it.
		 */
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
		// Other options available for storing team result is by using Strings
		// [Loss/Win] and Characters[L/w]
		// but I prefer boolean list so that further modification could be easier
	}

	@Data /**
			 * This annotation Generates getters for all fields, a useful toString method,
			 * and hashCode and equals implementations. Will also generate setters for all
			 * non-final fields, as well as a constructor.
			 */
	@NoArgsConstructor /**
						 * This annotation Generates getters for all fields, a useful toString method,
						 * and hashCode and equals implementations. Will also generate setters for all
						 * non-final fields, as well as a constructor.
						 */
	@AllArgsConstructor /**
						 * This annotation Generates an all-args constructor.An all-args constructor
						 * requires one argument for every field in the class.
						 */
	// static inner class
	public static class Team {
		private String teamName;
		private Integer points;
		private List<Boolean> results;
	}

	public static void main(String[] args) {
		// The run method actuates the spring boot application
		SpringApplication.run(TestvagarantApplication.class, args);

		// In the below code the generalized method is hard coded and called twice to
		// display various result
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
		System.out.println("Teams\t:\tPoints\t:\tLast Five Matches");
		System.out.println("-----------------------------------------------------------------------");
		avg = 0;
		List<Team> twoConsecutiveWinnigTeam = getConsecutiveWinnigOrLosingTeam(true, 3);
		for (Team e : twoConsecutiveWinnigTeam) {
			System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
			e.getResults().forEach(r -> System.out.print(r + "\t"));
			System.out.println();
			avg += e.getPoints();
		}
		try {
			System.out.println("The average points are : " + avg / twoConsecutiveWinnigTeam.size());
		} catch (ArithmeticException e) {
			System.err.println("No team matches the stat");
		}

		// Here, the same task is done with a scanner to allow user to enter the input
		// to perform various operation
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("-----Dynamic Output  --------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------");
		try {
			// User can be dumb and can enter incorrect values so suitable Exception
			// Handling was a necessity
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the number of match count to be considered as consecutive [ 2 <= N <=5 ]:");
			Integer count = Integer.parseInt(scanner.nextLine());
			// because we are handling consecutive matches so the minimum number of matches is 2
			if (count <= 1 || count > 5) {
				throw new InvalidAttributesException("number of win/loss count cannot be validated");
			}
			System.out.println("Enter the match result [Can be win or lose]");
			String result = scanner.nextLine().toUpperCase();

			avg = 0;
			if (!(result.equalsIgnoreCase("WIN") || result.equalsIgnoreCase("LOSE"))) {
				throw new InvalidAttributesException("number of win/loss count cannot be validated");
			}
			System.out.println("The " + count + " Consecutively "
					+ (result.equalsIgnoreCase("WIN") ? "Winning" : "Losing") + " teams are ");
			System.out.println("Teams\t:\tPoints\t:\tLast Five Matches");
			System.out.println("-----------------------------------------------------------------------");
			List<Team> consecutive = getConsecutiveWinnigOrLosingTeam(result.equalsIgnoreCase("WIN"), count);
			for (Team e : consecutive) {
				System.out.print(e.getTeamName() + "\t:\t" + e.getPoints() + "\t:\t");
				e.getResults().forEach(r -> System.out.print(r + "\t"));
				System.out.println();
				avg += e.getPoints();
			}
			try {
				System.out.println("The average points are : " + avg / consecutive.size());
			} catch (ArithmeticException e) {
				System.err.println("No team matches the stat");
			}
		} catch (Exception e) {
			System.err.println("Please Enter Proper values :" + e.getMessage());
		}
	}

	// Generalized method to teams with N consecutive loss or wins
	public static List<Team> getConsecutiveWinnigOrLosingTeam(Boolean result, Integer number) {
		// A local variable which will be used for returning a value
		List<Team> resultantTeams = new LinkedList<Team>();

		// Iteration is done using forEach loop, as using streams would have prohibited
		// us from modifying any field inside the stream
		/**
		 * Integer z = 0; Arrays.asList(new Object[] { 1, 2 }).forEach(e -> { z++; });
		 * the above code will return error "Local variable z defined in an enclosing
		 * scope must be final or effectively final"
		 */

		for (Team t : teams) {
			// array is taken just to store the results of team
			List<Boolean> results = t.getResults();

			/**
			 * Here the logic we are using is we will concatenate the results into a string
			 * and we will also generate the comparison string of result*number of matches
			 * 
			 * For example : Arrays.asList(true, true, false, false, true); will be taken as
			 * resultantString : "truetruefalsefalsetrue" and 2 consecutive loss will be
			 * taken as data : "falsefalse" 
			 * If data is present is resultant string then we can add the team to the list
			 */
			String resultantString = "";
			for (Boolean b : results) {
				resultantString += b;
			}

			String data = new String("");
			int i = 0;
			while (i < number) {
				data += result;
				i++;
			}
			if (resultantString.contains(data)) {
				resultantTeams.add(t);
			}
		}
		return resultantTeams;
	}

}
