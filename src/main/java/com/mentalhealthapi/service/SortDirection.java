package com.mentalhealthapi.service;

import org.springframework.data.domain.Sort.Direction;

public enum SortDirection {
    ASC(Direction.ASC),
    DESC(Direction.DESC);

    private final Direction direction;

    SortDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

