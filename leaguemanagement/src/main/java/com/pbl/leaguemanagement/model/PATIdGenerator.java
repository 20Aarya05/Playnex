package com.pbl.leaguemanagement.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.time.LocalDate;

public class PATIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Ensure the object is of type PlayerAndTeam
        if (!(object instanceof PlayerAndTeam)) {
            throw new IllegalArgumentException("Object is not of type PlayerAndTeam");
        }
        PlayerAndTeam playerAndTeam = (PlayerAndTeam) object;

        // Get current year and month
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        // Reverse the year and month
        String reversedYear = new StringBuilder(year).reverse().toString();   // e.g., 2024 -> 4202
        String reversedMonth = new StringBuilder(month).reverse().toString(); // e.g., 09 -> 90

        // Assign PAT type code
        String patTypeCode = "06"; // Code for PlayerAndTeam

        // Generate the prefix for the current period and PAT type
        String prefix = reversedYear + reversedMonth + patTypeCode; // e.g., 42029006

        // Fetch the maximum existing sequence for this prefix
        Long nextSequence = getNextSequence(session, prefix);

        // Format the sequence as a 4-digit number with leading zeros
        String sequenceStr = String.format("%04d", nextSequence);

        // Combine all parts to form the custom ID
        return prefix + sequenceStr; // e.g., 420290060001
    }

    private Long getNextSequence(SharedSessionContractImplementor session, String prefix) {
        // Query to find the maximum existing ID with the current prefix
        String hql = "SELECT MAX(pat.id) FROM PlayerAndTeam pat WHERE pat.id LIKE :prefixPattern";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("prefixPattern", prefix + "%");
        String maxId = query.uniqueResult();

        if (maxId == null) {
            // No existing ID with this prefix, start at 1
            return 1L;
        } else {
            // Extract the sequence part and increment
            String sequencePart = maxId.substring(prefix.length());
            try {
                Long currentMax = Long.parseLong(sequencePart);
                return currentMax + 1;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid ID format in database: " + maxId, e);
            }
        }
    }
}
