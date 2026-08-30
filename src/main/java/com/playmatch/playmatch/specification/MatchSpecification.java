package com.playmatch.playmatch.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.enums.SportType;

public class MatchSpecification {

    public static Specification<Match> hasSport(SportType sport) {

        return (root, query, criteriaBuilder) -> {

            if (sport == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("sport"),
                    sport
            );
        };
    }
    public static Specification<Match> titleContains(String title){
        return (root,query,criteriaBuilder)->{
            if(title==null || title.isEmpty()){
                return null;
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("title")),
                "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Match> matchTimeAfter(LocalDateTime dateTime){
        return (root, query, criteriaBuilder)->{
            if(dateTime==null){
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(
                root.<LocalDateTime>get("matchTime"),
                dateTime
            );
        };
    }

    public static Specification<Match> matchTimeBefore(LocalDateTime dateTime){
        return (root, query, criteriaBuilder)->{
            if(dateTime==null){
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(
                root.<LocalDateTime>get("matchTime"),
                dateTime
            );
        };
    }

    public static Specification<Match> availablePlayersGreaterThanZero(){
        return (root,query,criteriaBuilder)->{
            return criteriaBuilder.greaterThan(
                root.<Integer>get("maxPlayers"),
                root.<Integer>get("bookedPlayers")
            );
        };
    }

}
