package com.teamJava4.Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="History")

public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long history_id;
	private Long customer_id;
	private Timestamp watched_at;
	private Boolean isLike;
	private Timestamp likeDATE;
	public History(Long history_id, Long customer_id, Timestamp watched_at, Boolean isLike, Timestamp likeDATE) {
		super();
		this.history_id = history_id;
		this.customer_id = customer_id;
		this.watched_at = watched_at;
		this.isLike = isLike;
		this.likeDATE = likeDATE;
	}
	public History() {
		super();
	}
	public Long getHistory_id() {
		return history_id;
	}
	public void setHistory_id(Long history_id) {
		this.history_id = history_id;
	}
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public Timestamp getWatched_at() {
		return watched_at;
	}
	public void setWatched_at(Timestamp watched_at) {
		this.watched_at = watched_at;
	}
	public Boolean getIsLike() {
		return isLike;
	}
	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
	public Timestamp getLikeDATE() {
		return likeDATE;
	}
	public void setLikeDATE(Timestamp likeDATE) {
		this.likeDATE = likeDATE;
	}
	@Override
	public String toString() {
		return "History [history_id=" + history_id + ", customer_id=" + customer_id + ", watched_at=" + watched_at
				+ ", isLike=" + isLike + ", likeDATE=" + likeDATE + "]";
	}
	
	
	
}
