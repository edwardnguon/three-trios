package cs3500.threetrios.view;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the ThreeTriosView interface for testing purposes.
 * This mock simulates basic view behavior and tracks interactions with its methods.
 */
public class MockView implements ThreeTriosView {

  private final List<String> messages; // Tracks messages displayed to the user
  private boolean rendered; // Tracks whether render() was called

  public MockView() {
    this.messages = new ArrayList<>();
    this.rendered = false;
  }

  @Override
  public void render() {
    rendered = true; // Mark that render was called
    messages.add("Rendered view");
  }

  @Override
  public void addClickListener(Features listener) {
    //needed to be implemented but serves no purpose
    //in controller testing due to controller's separation from view
  }


  public void showErrorMessage(String message) {
    messages.add("Error: " + message);
  }


  public void showMessage(String message) {
    messages.add("Message: " + message);
  }

  /**
   * Checks if render() was called.
   *
   * @return True if render() was called, false otherwise.
   */
  public boolean wasRendered() {
    return rendered;
  }

  /**
   * Gets a list of all messages displayed by this mock view.
   *
   * @return A list of messages as strings.
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * Resets the rendered flag and clears all messages for reuse in tests.
   */
  public void reset() {
    rendered = false;
    messages.clear();
  }
}