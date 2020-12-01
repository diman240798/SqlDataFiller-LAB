package ru.sfedu.sqlserverdatafiller.sql

object NewRequests {
    val REQUEST_1 = "with blue_kills(team, kills) as (\n" +
            "select t.team_name, count(mr.b_kills) as kills from match_result  as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_kills(team, kills) as (\n" +
            "select t.team_name, count(mr.r_kills) as kills from match_result  as mr\n" +
            "\tjoin match as m on m.match_result_id = mr.id\n" +
            "\tjoin team as t on m.r_team_id = t.id\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_kills(team, kills) as (\n" +
            "select team, kills from blue_kills union select team, kills from red_kills\n" +
            ")\n" +
            "\n" +
            "select team, sum(kills) as kills from all_kills group by team order by kills DESC"
    val REQUEST_2 = "with blue_wins(team, wins) as (\n" +
            "select t.team_name, count(*) as wins from match_result  as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "\twhere mr.b_result > mr.r_result\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_wins(team, wins) as (\n" +
            "select t.team_name, count(*) as wins from match_result  as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.r_team_id = t.id\n" +
            "\twhere mr.r_result > mr.b_result\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_wins(team, wins) as (\n" +
            "select team, wins from blue_wins union select team, wins from red_wins\n" +
            ")\n" +
            "\n" +
            "select team, sum(wins) as wins from all_wins group by team order by wins DESC"
    val REQUEST_3 = "with blue_loses(team, loses) as (\n" +
            "select t.team_name, count(*) as loses from match_result  as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "\twhere mr.b_result < mr.r_result\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_loses(team, loses) as (\n" +
            "select t.team_name, count(*) as loses from match_result  as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.r_team_id = t.id\n" +
            "\twhere mr.r_result < mr.b_result\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_loses(team, loses) as (\n" +
            "select team, loses from blue_loses union select team, loses from red_loses\n" +
            ")\n" +
            "\n" +
            "select team, sum(loses) as loses from all_loses group by team order by loses DESC"
    val REQUEST_4 = "with blue_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.time > 15\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.r_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.time > 15\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_monsters(team, monsters) as (\n" +
            "select team, monsters from blue_monsters union select team, monsters from red_monsters\n" +
            ")\n" +
            "\n" +
            "select team, sum(monsters) as monsters from all_monsters group by team order by monsters DESC"

    val REQUEST_5 = "with fire_dragon as (\n" +
            "\tselect id from monster where type = 'Fire' and name = 'Dragon'\n" +
            "),\n" +
            "\n" +
            "blue_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.monster_id = (select id from fire_dragon) and tm.team_color = 'blue'\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.r_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.monster_id = (select id from fire_dragon) and tm.team_color = 'red'\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_monsters(team, monsters) as (\n" +
            "select team, monsters from blue_monsters union select team, monsters from red_monsters\n" +
            ")\n" +
            "\n" +
            "select team, sum(monsters) as monsters from all_monsters group by team order by monsters DESC"
    val REQUEST_6 = "with nashor as (\n" +
            "\tselect top 1 id from monster where name = 'Baron Nashor'\n" +
            "),\n" +
            "\n" +
            "blue_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.b_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.monster_id = (select top 1 id from nashor) and tm.team_color = 'blue'\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "red_monsters(team, monsters) as (\n" +
            "select t.team_name, count(*) as monsters from match_result as mr\n" +
            "\tjoin match m on m.match_result_id = mr.id\n" +
            "\tjoin team t on m.r_team_id = t.id\n" +
            "\tjoin taken_monster tm on tm.match_result_id = mr.id\n" +
            "\twhere tm.monster_id = (select top 1 id from nashor) and tm.team_color = 'red'\n" +
            "group by t.team_name\n" +
            "),\n" +
            "\n" +
            "all_monsters(team, monsters) as (\n" +
            "select team, monsters from blue_monsters union select team, monsters from red_monsters\n" +
            ")\n" +
            "\n" +
            "select team, sum(monsters) as monsters from all_monsters group by team order by monsters DESC"
    val REQUEST_7 = "with blue_monsters(name, count) as (\n" +
            "select mn.name as name, count(*) as count from monster as mn\n" +
            "\tjoin taken_monster tm on tm.monster_id = mn.id\n" +
            "\tjoin match_result mr on tm.match_result_id = mr.id\n" +
            "\tjoin match mt on mr.id = mt.match_result_id\n" +
            "\tjoin team t on mt.b_team_id = t.id\n" +
            "\twhere tm.team_color = 'blue'\n" +
            "group by mn.name\n" +
            "),\n" +
            "\n" +
            "red_monsters(name, count) as (\n" +
            "select mn.name as name, count(*) as count from monster as mn\n" +
            "\tjoin taken_monster tm on tm.monster_id = mn.id\n" +
            "\tjoin match_result mr on tm.match_result_id = mr.id\n" +
            "\tjoin match mt on mr.id = mt.match_result_id\n" +
            "\tjoin team t on mt.r_team_id = t.id\n" +
            "\twhere tm.team_color = 'red'\n" +
            "group by mn.name\n" +
            "),\n" +
            "\n" +
            "all_monsters(name, count) as (\n" +
            "select name, count from blue_monsters union select name, count from red_monsters\n" +
            ")\n" +
            "\n" +
            "select name, sum(count) as count from all_monsters group by name order by count DESC"
    val REQUEST_8 = "with blue_teams(team) as (\n" +
            "select t.team_name as team from team t\n" +
            "join match m on t.id = m.b_team_id\n" +
            "join champions_pick cp on cp.id = m.b_comp_id\n" +
            "where cp.adc_name = 'jinx'\n" +
            "),\n" +
            "\n" +
            "red_teams(team) as (\n" +
            "\tselect t.team_name as team from team t\n" +
            "\tjoin match m on t.id = m.r_team_id\n" +
            "\tjoin champions_pick cp on cp.id = m.r_comp_id\n" +
            "\twhere cp.adc_name = 'jinx'\n" +
            "),\n" +
            "\n" +
            "all_team(team) as (\n" +
            "\tselect team from blue_teams union select team from red_teams\n" +
            ")\n" +
            "\n" +
            "select distinct team as count from all_team group by team"
    val REQUEST_9 = "with blue_teams(team, gold) as (\n" +
            "\tselect t.team_name as team, max(gp.gold_per_minute) from team t\n" +
            "\tjoin match m on t.id = m.b_team_id\n" +
            "\tjoin match_result mr on mr.id = m.match_result_id\n" +
            "\tjoin gold g on g.id = mr.b_gold_id\n" +
            "\tjoin gold_gold_per_minute gp on gp.gold_id = g.id\n" +
            "group by mr.id, t.team_name\n" +
            "),\n" +
            "\n" +
            "red_teams(team, gold) as (\n" +
            "\tselect t.team_name as team, max(gp.gold_per_minute) from team t\n" +
            "\tjoin match m on t.id = m.r_team_id\n" +
            "\tjoin match_result mr on mr.id = m.match_result_id\n" +
            "\tjoin gold g on g.id = mr.r_gold_id\n" +
            "\tjoin gold_gold_per_minute gp on gp.gold_id = g.id\n" +
            "group by mr.id, t.team_name\n" +
            "),\n" +
            "\n" +
            "all_teams(team, gold) as (\n" +
            "\tselect team, gold from blue_teams union select team, gold from red_teams\n" +
            ")\n" +
            "\n" +
            "select team from all_teams order by gold"
    val REQUEST_10 = "with team_fty(team) as (\n" +
            "\tselect distinct t.team_name as team from team t\n" +
            "\tjoin match m on t.id = m.r_team_id or t.id = m.b_team_id\n" +
            "\tjoin tournament tr on tr.id = m.tournament_id\n" +
            "\twhere tr.year = 2015\n" +
            "),\n" +
            "\n" +
            "team_sty(team) as (\n" +
            "\tselect distinct t.team_name as team from team t\n" +
            "\tjoin match m on t.id = m.r_team_id or t.id = m.b_team_id\n" +
            "\tjoin tournament tr on tr.id = m.tournament_id\n" +
            "\twhere tr.year = 2017\n" +
            ")\n" +
            "\n" +
            "select team as team from team_fty INTERSECT select team as team from team_sty"
}