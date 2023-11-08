package edu.trinity.got;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMemberDAO implements MemberDAO {
    private final Collection<Member> allMembers =
            MemberDB.getInstance().getAllMembers();

    @Override
    public Optional<Member> findById(Long id) {
        return getAll().stream()
                .filter(member -> member.id().equals(id))
                .findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return getAll().stream()
                .filter(member -> member.name().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAllByHouse(House house) {
        return getAll().stream()
                .filter(member -> member.house().equals(house))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Member> getAll() {
        return allMembers;
    }

    /**
     * Find all members whose name starts with S and sort by id (natural sort)
     */
    @Override
    public List<Member> startWithSandSortAlphabetically() {
        return getAll().stream()
                .filter(member -> member.name().startsWith("S"))
                .sorted(Comparator.comparing(Member::name))
                .collect(Collectors.toList());
    }

    /**
     * Final all Lannisters and sort them by name
     */
    @Override
    public List<Member> lannisters_alphabeticallyByName() {
        return getAll().stream()
                .filter(member -> member.house().equals(House.LANNISTER))
                .sorted(Comparator.comparing(Member::name))
                .collect(Collectors.toList());
    }

    /**
     * Find all members whose salary is less than the given value and sort by house
     */
    @Override
    public List<Member> salaryLessThanAndSortByHouse(double max) {
       return getAll().stream()
               .filter(member -> member.salary() < max)
               .sorted(Comparator.comparing(Member::house))
               .collect(Collectors.toList());
    }

    /**
     * Sort members by House, then by name
     */
    @Override
    public List<Member> sortByHouseNameThenSortByNameDesc() {
        return getAll().stream()
                .sorted(Comparator.comparing(Member::house).thenComparing(Member::name))
                .collect(Collectors.toList());
    }

    /**
     * Sort the members of a given House by birthdate
     */
    @Override
    public List<Member> houseByDob(House house) {
        return getAll().stream()
                .filter(member -> member.house().equals(house))
                .sorted(Comparator.comparing(Member::dob))
                .collect(Collectors.toList());
    }

    /**
     * Find all Kings and sort by name in descending order
     */
    @Override
    public List<Member> kingsByNameDesc() {
        return getAll().stream()
                .filter(member -> member.title().equals(Title.KING))
                .sorted(Comparator.comparing(Member::name))
                .collect(Collectors.toList());
    }

    /**
     * Find the average salary of all the members
     */
    @Override
    public double averageSalary() {
        List<Double> salaries = getAll().stream()
                .map(Member::salary)
                .toList();

        double total = 0;
        for(double val : salaries){ total += val; }
        return total/salaries.size();
    }

    /**
     * Get the names of a given house, sorted in natural order
     * (note sort by _names_, not members)
     */
    @Override
    public List<String> namesSorted(House house) {
        return getAll().stream()
                .filter(member -> member.house().equals(house))
                .map(Member::name)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    /**
     * Are any of the salaries greater than 100K?
     */
    @Override
    public boolean salariesGreaterThan(double max) {
        List<Member> acc = getAll().stream()
                .filter(member -> member.salary() > max)
                .toList();
        return !(acc.isEmpty());
    }

    /**
     * Are there any members of given house?
     */
    @Override
    public boolean anyMembers(House house) {
        List<Member> any = getAll().stream()
                .filter(member -> member.house().equals(house))
                .toList();
        return !(any.isEmpty());
    }

    /**
     * How many members of a given house are there?
     */
    @Override
    public long howMany(House house) {
        List<Member> num = getAll().stream()
                .filter(member -> member.house().equals(house))
                .toList();
        return num.size();
    }

    /**
     * Return the names of a given house as a comma-separated string
     */
    @Override
    public String houseMemberNames(House house) {
        String list = getAll().stream()
                .filter(member -> member.house().equals(house))
                .map(Member::name)
                .collect(Collectors.joining(", "));

        return list;
    }

    /**
     * Who has the highest salary?
     */
    @Override
    public Optional<Member> highestSalary() {
        return getAll().stream()
                .sorted(Comparator.comparingDouble(Member::salary).reversed())
                .findFirst();
    }

    /**
     * Partition members into royalty and non-royalty
     * (note: royalty are KINGs and QUEENS only)
     */
    @Override
    public Map<Boolean, List<Member>> royaltyPartition() {
        Map<Boolean, List<Member>> outMap = new HashMap<>();

        outMap.put(true, getAll().stream()
                .filter(member -> member.title().equals(Title.KING) || member.title().equals(Title.QUEEN))
                .collect(Collectors.toList()));
        outMap.put(false, getAll().stream()
                .filter(member -> !member.title().equals(Title.KING) && !member.title().equals(Title.QUEEN))
                .collect(Collectors.toList()));
        return outMap;
    }

    /**
     * Group members into Houses
     */
    @Override
    public Map<House, List<Member>> membersByHouse() {
        return getAll().stream()
                .collect(Collectors.groupingBy(Member::house));
    }

    /**
     * How many members are in each house?
     * (group by house, downstream collector using counting
     */
    @Override
    public Map<House, Long> numberOfMembersByHouse() {
        return getAll().stream()
                .collect(Collectors.groupingBy(Member::house, Collectors.counting()));
    }

    /**
     * Get the max, min, and ave salary for each house
     */
    @Override
    public Map<House, DoubleSummaryStatistics> houseStats() {
        return getAll().stream()
                .collect(Collectors.groupingBy(Member::house, Collectors.summarizingDouble(Member::salary)));
    }

}
