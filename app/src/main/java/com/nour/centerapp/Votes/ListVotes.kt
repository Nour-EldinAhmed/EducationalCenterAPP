package com.nour.centerapp.Votes

class ListVotes {
    var id:String?=null
    var vote:String?=null
    var timestamp:String?=null
    constructor(){

    }
    constructor( id:String, vote:String, timestamp: String){
        this.id=id
        this.vote=vote
        this.timestamp=timestamp
    }


}