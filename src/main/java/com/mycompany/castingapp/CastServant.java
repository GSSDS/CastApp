package com.mycompany.castingapp;

import CastApp.*;

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
