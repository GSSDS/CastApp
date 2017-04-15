package com.mycompany.castapp;

import com.mycompany.castapp.CastApp._CastOnImplBase;

class CastServant extends _CastOnImplBase
{
    public String CastOn()
    {
		System.out.println("Received a call.");
		boolean CastOn = false;
		
		if(!CastOn)
                {
                    CastOn = true;
                }
		
		return "\n Cast Device on " +CastOn+ "";
    }
}
