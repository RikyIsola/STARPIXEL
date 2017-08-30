package com.island.starpixel;
import android.graphics.*;
import android.view.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.armi.*;
import com.island.starpixel.blocchi.solidi.scudi.*;
import java.io.*;
import java.util.*;
public class Comandi extends Finestra
{
	private Gruppo g,sensori,motori,teletrasporto,armi,rotazione;
	private Gioco gioco;
	private ChunkLan attuale;
	private boolean selezioneTeletrasporto,direzioneTeletrasporto,selezioneArma;
	private EntitaLan teletrasportoSelezionato,armaSelezionata;
	private Bottone bottoneTeletrasportoSu,bottoneTeletrasportoGiu,su,giu,destra,sinistra,armisu,armigiu,armidestra,armisinistra,armicentro;
	private Testo aiutoTeletrasporto,percentuale;
	private Suono suoni;
	private Blocchi blocchi;
	private Lista<EntitaLan>scudi;
	private String perc="%";
	Comandi(Gioco gioco)
	{
		super(gioco.schermo());
		blocchi=schermo().blocchi;
		this.gioco=gioco;
		g=new Gruppo(this,10,10);
		scudi=new Lista<EntitaLan>();
		for(EntitaLan e:gioco.tu.chunk.entita)
		{
			if(sensori==null&&e.b==schermo().blocchi.Sensori)
			{
				sensori=new Gruppo(g,5,5,10,10,10,10);
				attuale=gioco.tu.chunk;
				new Bottone(sensori,0,0,5,1).scrivi("+").setOnClickListener(piu());
				new Bottone(sensori,5,0,10,1).scrivi("-").setOnClickListener(meno());
				ChunkLan csu=null;
				ChunkLan cgiu=null;
				ChunkLan cdestra=null;
				ChunkLan csinistra=null;
				for(ChunkLan chunk:gioco.chunk)
				{
					int x=chunk.x-gioco.tu.chunk.x;
					int y=chunk.y-gioco.tu.chunk.y;
					if(y==-1)csu=chunk;
					else if(x==1)cdestra=chunk;
					else if(y==1)cgiu=chunk;
					else if(x==-1)csinistra=chunk;
					chunk.migra(sensori);
					chunk.setOnTouchListener(evento());
					chunk.antiScroll(false,false);
					double menoX=0;
					double menoY=0;
					double piuX=0;
					double piuY=0;
					for(EntitaLan e1:chunk.entita)
					{
						if(e1.x()<menoX)menoX=e1.x();
						if(e1.y()<menoY)menoY=e1.y();
						if(e1.larghezza()>piuX)piuX=e1.larghezza();
						if(e1.altezza()>piuY)piuY=e1.altezza();
					}
					chunk.meno(menoX,menoY);
					chunk.piu(piuX-chunk.maxX(),piuY-chunk.maxY());
					chunk.copertura=new Oggetto(chunk,0,0,chunk.maxX(),chunk.maxY()).antiTrans(true,true);
					if(chunk.stato.equals(schermo().blocchi.statoNebula)&&chunk!=gioco.tu.chunk)chunk.copertura.colore(Color.rgb(143,0,255));
				}
				su=new Bottone(sensori,4,1,6,2);
				sinistra=new Bottone(sensori,0,4,1,6);
				giu=new Bottone(sensori,4,9,6,10);
				destra=new Bottone(sensori,9,4,10,6);
				su.scrivi("<").rotazione(90).setOnClickListener(freccia(giu,csu));
				giu.scrivi(">").rotazione(90).setOnClickListener(freccia(su,cgiu));
				destra.scrivi(">").setOnClickListener(freccia(sinistra,cdestra));
				sinistra.scrivi("<").setOnClickListener(freccia(destra,csinistra));
			}
			else if(teletrasporto==null&&e.b==schermo().blocchi.Teletrasporto)
			{
				teletrasporto=new Gruppo(g,0,0,3.5,5,10,5);
				bottoneTeletrasportoGiu=new Bottone(teletrasporto,1,3,9,4);
				bottoneTeletrasportoGiu.scrivi(schermo().lingua.teletrasportaGiu).setOnClickListener(teletrasporto(false));
				bottoneTeletrasportoSu=new Bottone(teletrasporto,1,1,9,2);
				bottoneTeletrasportoSu.scrivi(schermo().lingua.teletrasportaSu).setOnClickListener(teletrasporto(true));
				aiutoTeletrasporto=new Testo(teletrasporto,1,2,9,3,"");
			}
			else if(motori==null&&e.b==schermo().blocchi.Motore)
			{
				motori=new Gruppo(g,0,5,5,10,5,5);
				new Testo(motori,1,2,4,3,schermo().lingua.nome(e.b));
				new Bottone(motori,2,0,3,1).scrivi("<").rotazione(90).setOnClickListener(motori("0"));
				new Bottone(motori,2,4,3,5).scrivi(">").rotazione(90).setOnClickListener(motori("1"));
				new Bottone(motori,4,2,5,3).scrivi(">").setOnClickListener(motori("2"));
				new Bottone(motori,0,2,1,3).scrivi("<").setOnClickListener(motori("3"));
			}
			else if(armi==null&&e.b instanceof Arma)
			{
				armi=new Gruppo(g,3.5,0,6.5,5,5,5);
				armisu=new Bottone(armi,2,0,3,1);
				armisu.scrivi("<").rotazione(90).setOnClickListener(armi("0"));
				armisu.setVisibility(View.INVISIBLE);
				armigiu=new Bottone(armi,2,4,3,5);
				armigiu.scrivi(">").rotazione(90).setOnClickListener(armi("1"));
				armigiu.setVisibility(View.INVISIBLE);
				armidestra=new Bottone(armi,4,2,5,3);
				armidestra.scrivi(">").setOnClickListener(armi("2"));
				armidestra.setVisibility(View.INVISIBLE);
				armisinistra=new Bottone(armi,0,2,1,3);
				armisinistra.scrivi("<").setOnClickListener(armi("3"));
				armisinistra.setVisibility(View.INVISIBLE);
				armicentro=new Bottone(armi,1,2,4,3);
				armicentro.larghezzaX(0.5);
				armicentro.scrivi(schermo().lingua.selezionaArma).setOnClickListener(armi());
			}
			else if(rotazione==null&&e.b==schermo().blocchi.Rotatore)
			{
				rotazione=new Gruppo(g,6.5,0,10,5,5,5);
				new Bottone(rotazione,1,1,4,2).scrivi(schermo().lingua.rotea).setOnClickListener(rotea());
			}
			else if(e.b instanceof Scudo)scudi=Lista.aggiungi(scudi,e);
		}
		if(rotazione==null)rotazione=new Gruppo(g,6.5,0,10,5,5,5);
		percentuale=new Testo(rotazione,0,3,5,4,"");
		g.aggiorna();
	}
	public void onStop()
	{
		if(suoni!=null)
		{
			suoni=suoni.rilascia();
			new Suono(schermo(),R.raw.litewave_powerdown).start();
		}
		if(sensori!=null)for(final ChunkLan chunk:gioco.chunk)
		{
			if(chunk.getLayoutParams()!=null)
			{
				chunk.migra(gioco);
				chunk.antiScroll(true,true);
				chunk.antiPiu(false);
				chunk.max(blocchi.vista,blocchi.vista);
				chunk.copertura=null;
				schermo().runOnUiThread(new Runnable()
				{
					public void run()
					{
						chunk.setOnTouchListener(gioco.touch());
						chunk.removeView(chunk.copertura);
					}
				});
			}
		}
		gioco.superordine=true;
		super.onStop();
	}
	public void sempre()
	{
		if(attuale!=null)
		{
			//attuale.sempre();
			for(EntitaLan e:attuale.entita)e.sempre();
		}
	}
	public void sempreGrafico()
	{
		ChunkLan c=attuale;
		if(c==null)c=gioco.tu.chunk;
		int massimo=0;
		int somma=0;
		for(EntitaLan e:c.entita)
		{
			if(e.b instanceof Scudo)
			{
				massimo+=e.b.vita();
				somma+=e.rottura();
			}
		}
		int calcolo;
		if(massimo==0)calcolo=0;
		else calcolo=somma*100/massimo;
		percentuale.scrivi(schermo().lingua.scudi+":"+calcolo+perc);
		if(attuale!=null)attuale.sempre();
		if(teletrasporto!=null)
		{
			if(selezioneTeletrasporto)
			{
				if(direzioneTeletrasporto)bottoneTeletrasportoGiu.setVisibility(View.INVISIBLE);
				else bottoneTeletrasportoSu.setVisibility(View.INVISIBLE);
				if(teletrasportoSelezionato==null)aiutoTeletrasporto.scrivi(schermo().lingua.scegliTeletrasporto);
				else aiutoTeletrasporto.scrivi(schermo().lingua.scegliPunto);
			}
			else
			{
				bottoneTeletrasportoGiu.setVisibility(View.VISIBLE);
				bottoneTeletrasportoSu.setVisibility(View.VISIBLE);
				aiutoTeletrasporto.scrivi("");
			}
		}
		if(gioco.ordine&&gioco.tu!=null)
		{
			for(EntitaLan e:attuale.entita)if(e.b instanceof Solido)e.bringToFront();
			if(attuale.copertura!=null)attuale.copertura.bringToFront();
			gioco.ordine=false;
		}
		g.aggiorna();
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	private View.OnTouchListener evento()
	{
		return new View.OnTouchListener()
		{
			public boolean onTouch(View p1,MotionEvent p2)
			{
				if(selezioneTeletrasporto)
				{
					if(p2.getAction()==MotionEvent.ACTION_DOWN)
					{
						if(teletrasportoSelezionato==null)
						{
							if(attuale==gioco.tu.chunk)teletrasportoSelezionato=attuale.trovaTeletrasporto(p2.getX()/attuale.unitaX()+attuale.transX(),p2.getY()/attuale.unitaY()+attuale.transY());
						}
						else
						{
							double x=p2.getX()/attuale.unitaX()+attuale.transX();
							double y=p2.getY()/attuale.unitaY()+attuale.transY();
							int chunkx=attuale.x;
							int chunky=attuale.y;
							if(!direzioneTeletrasporto)
							{
								if(attuale.trovaSolido(x,y)==null)
								{
									gioco.lan.manda(schermo().blocchi.teletrasporta);
									gioco.lan.manda(String.valueOf(teletrasportoSelezionato.chunk.x));
									gioco.lan.manda(String.valueOf(teletrasportoSelezionato.chunk.y));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(teletrasportoSelezionato.x(),2)));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(teletrasportoSelezionato.y(),2)));
									gioco.lan.manda(String.valueOf(chunkx));
									gioco.lan.manda(String.valueOf(chunky));
									if(x<0)x--;
									if(y<0)y--;
									gioco.lan.manda(String.valueOf((int)x));
									gioco.lan.manda(String.valueOf((int)y));
								}
							}
							else
							{
								if(teletrasportoSelezionato.chunk.trovaSolido(teletrasportoSelezionato.x(),teletrasportoSelezionato.y())==null)
								{
									gioco.lan.manda(schermo().blocchi.teletrasporta);
									gioco.lan.manda(String.valueOf(attuale.x));
									gioco.lan.manda(String.valueOf(attuale.y));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(x,2)));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(y,2)));
									gioco.lan.manda(String.valueOf(teletrasportoSelezionato.chunk.x));
									gioco.lan.manda(String.valueOf(teletrasportoSelezionato.chunk.y));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(teletrasportoSelezionato.x(),2)));
									gioco.lan.manda(String.valueOf(Lista.arrotonda(teletrasportoSelezionato.y(),2)));
								}
							}
							selezioneTeletrasporto=false;
							teletrasportoSelezionato=null;
						}
					}
				}
				else if(selezioneArma)
				{
					if(p2.getAction()==MotionEvent.ACTION_DOWN&&attuale==gioco.tu.chunk)
					{
						EntitaLan e=attuale.trovaArma(p2.getX()/attuale.unitaX()+attuale.transX(),p2.getY()/attuale.unitaY()+attuale.transY());
						if(e!=null)
						{
							armaSelezionata=e;
							armisu.setVisibility(View.VISIBLE);
							armigiu.setVisibility(View.VISIBLE);
							armidestra.setVisibility(View.VISIBLE);
							armisinistra.setVisibility(View.VISIBLE);
						}
					}
				}
				else Gruppo.onTouchEvent((Gruppo)p1,p2);
				return true;
			}
		};
	}
	private View.OnClickListener piu()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(attuale.maxX()>1)attuale.max(attuale.maxX()-1,attuale.maxY()-1);
				double menoX=0;
				double menoY=0;
				double piuX=0;
				double piuY=0;
				for(EntitaLan e1:attuale.entita)
				{
					if(e1.x()<menoX)menoX=e1.x();
					if(e1.y()<menoY)menoY=e1.y();
					if(e1.larghezza()>piuX)piuX=e1.larghezza();
					if(e1.altezza()>piuY)piuY=e1.altezza();
				}
				attuale.meno(menoX,menoY);
				attuale.piu(piuX-attuale.maxX(),piuY-attuale.maxY());
				attuale.copertura.larghezza(attuale.maxX()).altezza(attuale.maxY());
			}
		};
	}
	private View.OnClickListener meno()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				double x=attuale.piuX()-attuale.menoX()+attuale.maxX();
				double y=attuale.piuY()-attuale.menoY()+attuale.maxY();
				double maggiore;
				if(x>y)maggiore=x;
				else maggiore=y;
				if(attuale.maxX()<maggiore)attuale.max(attuale.maxX()+1,attuale.maxY()+1);
				double menoX=0;
				double menoY=0;
				double piuX=0;
				double piuY=0;
				for(EntitaLan e1:attuale.entita)
				{
					if(e1.x()<menoX)menoX=e1.x();
					if(e1.y()<menoY)menoY=e1.y();
					if(e1.larghezza()>piuX)piuX=e1.larghezza();
					if(e1.altezza()>piuY)piuY=e1.altezza();
				}
				attuale.meno(menoX,menoY);
				attuale.piu(piuX-attuale.maxX(),piuY-attuale.maxY());
				attuale.copertura.larghezza(attuale.maxX()).altezza(attuale.maxY());
			}
		};
	}
	private View.OnClickListener teletrasporto(final boolean su)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				selezioneTeletrasporto=true;
				direzioneTeletrasporto=su;
			}
		};
	}
	private View.OnClickListener freccia(final Bottone speciale,final ChunkLan nuovo)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(speciale.getVisibility()==View.VISIBLE)
				{
					su.setVisibility(View.INVISIBLE);
					giu.setVisibility(View.INVISIBLE);
					destra.setVisibility(View.INVISIBLE);
					sinistra.setVisibility(View.INVISIBLE);
					speciale.setVisibility(View.VISIBLE);
					attuale.setVisibility(View.INVISIBLE);
					attuale=nuovo;
					attuale.setVisibility(View.VISIBLE);
				}
				else
				{
					su.setVisibility(View.VISIBLE);
					giu.setVisibility(View.VISIBLE);
					destra.setVisibility(View.VISIBLE);
					sinistra.setVisibility(View.VISIBLE);
					attuale.setVisibility(View.INVISIBLE);
					attuale=gioco.tu.chunk;
					attuale.setVisibility(View.VISIBLE);
				}
			}
		};
	}
	private View.OnClickListener motori(final String n)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				gioco.lan.manda(schermo().blocchi.motori);
				gioco.lan.manda(n);
				suoni=new Suono(schermo(),R.raw.litewave_powerup).start();
			}
		};
	}
	private View.OnClickListener armi(final String n)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(armaSelezionata!=null)
				{
					gioco.lan.manda(schermo().blocchi.armi);
					gioco.lan.manda(String.valueOf(Lista.arrotonda(armaSelezionata.x(),2)));
					gioco.lan.manda(String.valueOf(Lista.arrotonda(armaSelezionata.y(),2)));
					gioco.lan.manda(n);
					/*if(armaSelezionata.b==schermo().blocchi.GeneratoreBuchiNeri)
					{
						int chunkX=gioco.tu.chunk.x;
						int chunkY=gioco.tu.chunk.y;
						int rotazione=Integer.valueOf(n);
						if(rotazione==0)chunkY--;
						else if(rotazione==1)chunkY++;
						else if(rotazione==2)chunkX++;
						else chunkX--;
						ChunkLan arrivo=null;
						for(ChunkLan c:gioco.chunk)if(c.x==chunkX&&c.y==chunkY)arrivo=c;
						arrivo.stato(schermo().blocchi.statoNero);
					}*/
				}
			}
		};
	}
	private View.OnClickListener armi()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				Bottone b=(Bottone)p1;
				selezioneArma=!selezioneArma;
				if(selezioneArma)b.colore(Color.DKGRAY,Color.LTGRAY);
				else b.colore(Color.LTGRAY,Color.DKGRAY);
			}
		};
	}
	private View.OnClickListener rotea()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				gioco.lan.manda(schermo().blocchi.rotazione);
			}
		};
	}
}
