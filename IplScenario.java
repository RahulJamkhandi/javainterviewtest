package TestVagarant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class IplScenario {

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
		System.out.println("The two Consecutively losing teams are ");
		int avg=0;
		List<Team> twoConsecutiveLosingTeam = getConsecutiveWinnigOrLosingTeam(false, 2);
		for (Team t : twoConsecutiveLosingTeam) {
			System.out.println("\t"+t);
			avg+=t.getPoints();
		}
		System.out.println("The average points are : "+avg/twoConsecutiveLosingTeam.size());
		
		System.out.println("The Three Consecutively winning teams are ");
		avg=0;
		List<Team> twoConsecutiveWinnigTeam = getConsecutiveWinnigOrLosingTeam(true, 2);
		for (Team t : twoConsecutiveWinnigTeam) {
			System.out.println("\t"+t);
			avg+=t.getPoints();
		}
		System.out.println("The average points are : "+avg/twoConsecutiveWinnigTeam.size());
		
	}

	// for N consecutive losses
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
							if ((Boolean) array[i]==result && (Boolean) array[i+j]==result && (Boolean) array[i] == (Boolean) array[i + j]) {
								similar--;
							}
						}
						if (similar==0) {
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
