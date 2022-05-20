import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as d3 from 'd3'
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user = new User('', 1)

  constructor(private http: HttpClient) { }
  
  ngOnInit(): void {
    this.http.get<any>('api/user/loggedInUser/').subscribe(
      response => {
        this.user = response
      }
    );
    this.http.get<any>('api/treeView/').subscribe(
      response => {
        console.log(response)
        this.makeTree(response)
      }
    );
    // blocked by cors, fix this
    // this.http.post('https://localhost/api/login', {username: "admin", password: "123"}).subscribe(
    //   response => {
    //     console.log("login response: " + response)
    //   }
    // );
  }

  makeTree(data){
    // var data = {"name": "A", "children": [
    //             {"name": "C"},
    //             {"name": "D", "children": [
    //                 {"name": "F"},
    //                 {"name": "E"}
    //             ]},
    //             {"name": "B"}
    //         ]};
    var root = d3.hierarchy(data)
    var treeLayout = d3.tree()
      .size([580, 580]);
    treeLayout(root);
    var svg = d3.select("#demo1");

    svg.select('g.links')
        .selectAll('line.link')
        .data(root.links())
        .enter()
        .append('line')
        .attr('x1', function(d) {return d.source.x;})
        .attr('y1', function(d) {return d.source.y;})
        .attr('x2', function(d) {return d.target.x;})
        .attr('y2', function(d) {return d.target.y;})
        .attr('stroke', "darkgray")
        .attr('stroke-width', 2);

    svg.select('g.nodes')
        .selectAll('circle.node')
        .data(root.descendants())
        .enter()
        .append('circle')
        .attr('cx', function(d) {return d.x;})
        .attr('cy', function(d) {return d.y;})
        .attr('r', 10)
        .attr("fill", "lightblue")
        .attr('stroke', "darkgray")
        .attr('stroke-width', 1);

    // Labels
    d3.select('g.nodes')
      .selectAll('text.label')
      .data(root.descendants())
      .join('text')
      .classed('label', true)
      .attr('x', function(d) {return d.x;})
      .attr('y', function(d) {return d.y - 10;})
      .text(function(d) {
        //console.log(d.data.name)
        return d.data.id;
      });
  }
}