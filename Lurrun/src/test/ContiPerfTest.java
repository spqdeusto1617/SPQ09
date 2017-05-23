package es.deusto.server;
import java.util.List;
import java.util.Random;
import org.junit.Rule;
import org.junit.*;
import org.databene.contiperf.junit.*;
import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.report.EmptyReportModule;
import org.databene.contiperf.junit.ContiPerfRule;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junit.framework.JUnit4TestAdapter;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.After;
//import org.junit.Ignore;

import es.deusto.server.db.*;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.IRemote;
import es.deusto.server.remote.Remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;


import javax.swing.plaf.synth.SynthSeparatorUI;



public class ContiPerfTest {




	int count = 0;
	int count1 = 0;
	String a = ""+count;
	String b = ""+count;
	Random rand = new Random();
	IDB db = new DB();


	final static Logger logger = LoggerFactory.getLogger(ContiPerfTest.class);

	@Rule public ContiPerfRule rule = new ContiPerfRule();

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ContiPerfTest.class);
	}


	/*
	@Test
	@PerfTest(duration = 5000)
	@Required(average =5000,throughput = 70)
	public void storeGameInvocationFail() {
		Company cc = new Company("PerfTestCompany");
		Genre gr = new Genre("PerfTestGenre");
		Game g = new Game("Perftest" + a, rand.nextInt(5) + 1, 0);

		db.addGameToDb(g, gr, cc);
		count++;

	}
	*/
	@Test
	@PerfTest(duration = 5000)
	public void storeGameDuration() {

		Company cc = new Company("PerfTestCompany");
		Genre gr = new Genre("PerfTestGenre");
		Game g = new Game("Perftest" + a, rand.nextInt(5) + 1, 0);

		db.addGameToDb(g, gr, cc);
		count++;

	}


	@Test
	@PerfTest(invocations = 50, threads = 20)
	@Required(max = 20000 , median =8000)
	public void loadGame() {
		db.showGame("Perftest" + b);
		count1++;
		}

	@Test
	@PerfTest(invocations = 20, threads = 5)
	@Required(totalTime = 10000)
	public void loadGame2() {
		db.showGame("Perftest" + b);
		count1++;
		}


}
