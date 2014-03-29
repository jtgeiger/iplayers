package com.sibilantsolutions.iplayers.layer.app.tls.domain;

import java.util.ArrayList;
import java.util.List;

public class ClientHello
{

    private Version version;
    private Random random;
    private int sessionIdLength;
    private String sessionId;
    private int cipherSuitesLength;
    private List<CipherSuite> cipherSuites = new ArrayList<CipherSuite>();

    public List<CipherSuite> getCipherSuites()
    {
        return cipherSuites;
    }

    public int getCipherSuitesLength()
    {
        return cipherSuitesLength;
    }

    public Random getRandom()
    {
        return random;
    }

    public Version getVersion()
    {
        return version;
    }

    public int getSessionIdLength()
    {
        return sessionIdLength;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public static ClientHello parse( String data )
    {
        ClientHello ch = new ClientHello();

        int i = 0;

        ch.version = Version.valueOf( data.charAt( i++ ), data.charAt( i++ ) );
        final int RANDOM_LENGTH = 32;
        ch.random = Random.parse( data.substring( i, i + RANDOM_LENGTH ) );
        i += RANDOM_LENGTH;
        ch.sessionIdLength = data.charAt( i++ );
        ch.sessionId = data.substring( i, i + ch.sessionIdLength );
        i += ch.sessionIdLength;
        ch.cipherSuitesLength = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
        for ( int j = 0; j < ch.cipherSuitesLength; j += 2 )
        {
            int csVal = data.charAt( i++ ) * 0x0100 + data.charAt( i++ );
            CipherSuite cs = CipherSuite.fromValue( csVal );
            ch.cipherSuites.add( cs );
        }


        return ch;
    }

}