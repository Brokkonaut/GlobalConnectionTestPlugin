package de.cubeside.testplugin;

import de.cubeside.globalserver.event.Event;
import de.cubeside.globalserver.event.EventHandler;
import de.cubeside.globalserver.event.Listener;
import de.cubeside.globalserver.event.Priority;
import de.cubeside.globalserver.event.globalserver.GlobalServerStartedEvent;
import de.cubeside.globalserver.plugin.Plugin;

public class TestPlugin extends Plugin {
    @Override
    public void onLoad() {
        getLogger().info("Hello World!");
        getServer().getEventBus().registerHandlers(new TestListener());
        getServer().getEventBus().dispatchEvent(new TestEvent("abc"));
        getServer().getEventBus().dispatchEvent(new AdvancedTestEvent("def", 2));
    }

    @Override
    public void onUnload() {
        getLogger().info("Goodbye World!");
    }

    private class TestListener implements Listener {
        @EventHandler
        public void onGlobalServerStarted(GlobalServerStartedEvent event) {
            getLogger().info("GlobalServerStarted");
        }

        @EventHandler(priority = Priority.EARLY)
        public void onEarlyTest(TestEvent event) {
            getLogger().info("onEarlyTest: " + event.getValue());
        }

        @EventHandler(priority = Priority.LATE)
        public void onLateTest(TestEvent event) {
            getLogger().info("onLateTest: " + event.getValue());
        }

        @EventHandler
        public void onTest(TestEvent event) {
            getLogger().info("onTest: " + event.getValue());
        }

        @EventHandler
        public void onAdvancedTest(AdvancedTestEvent event) {
            getLogger().info("onAdvancedTest: " + event.getValue() + " -> " + event.getValue2());
        }
    }

    public class TestEvent extends Event {
        private String value;

        public TestEvent(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public class AdvancedTestEvent extends TestEvent {
        private int value2;

        public AdvancedTestEvent(String value, int value2) {
            super(value);
            this.value2 = value2;
        }

        @Override
        public String getValue() {
            return "Advanced" + super.getValue();
        }

        public int getValue2() {
            return value2;
        }
    }
}
