package com.musicstreet.mobile;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by johnfranklin on 5/1/15.
 */
public class QueryFragment extends ContactFragment {
    private final String tablename;
    private final String searched;
    private final String columnname;
    private final String[] dependentTables;
    private final String usercolumnname;

    public QueryFragment(String tablename, String columnname, String usercolumnname, String searched, String[] dependentValues)
    {
        this.tablename = tablename;
        this.columnname = columnname;
        this.searched = searched;
        this.usercolumnname = usercolumnname;
        this.dependentTables = dependentValues;
    }
    public ParseQuery<ParseObject> getQueryType() {
        return ParseQuery.getQuery(tablename);
    }
    public ParseUser getProperUser(ParseObject obj) {
        return (ParseUser) obj.get(usercolumnname);
    }

    public void setQueryFactors(ParseQuery<ParseObject> query, ParseUser user) {
        for(int i = 0; i < dependentTables.length; i++)
            query.include(dependentTables[i]);
        query.include(usercolumnname);
        query.whereMatches(columnname, ".*" + modifyToRegexForIgnoreCase(searched) +".*");



    }
    public String modifyToRegexForIgnoreCase(String str)
    {
        String ret = "";
        for(int i = 0; i < str.length(); i++) {

            ret += "[" + Character.toUpperCase(str.charAt(i)) + Character.toLowerCase(str.charAt(i)) + "]";
        }
        return ret;
    }

}
