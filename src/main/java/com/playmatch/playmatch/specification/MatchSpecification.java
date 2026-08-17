package com.playmatch.playmatch.specification;

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
}
