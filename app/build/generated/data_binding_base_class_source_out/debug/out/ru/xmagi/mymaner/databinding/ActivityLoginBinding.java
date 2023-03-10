// Generated by view binder compiler. Do not edit!
package ru.xmagi.mymaner.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ru.xmagi.mymaner.R;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final EditText ServerHost;

  @NonNull
  public final ConstraintLayout container;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final ProgressBar loading;

  @NonNull
  public final EditText password;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   */
  @Nullable
  public final Button runMiner;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final Button startButton;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-w1240dp/</li>
   *   <li>layout-w936dp/</li>
   * </ul>
   */
  @Nullable
  public final TextView textView;

  @NonNull
  public final EditText worker;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @Nullable EditText ServerHost,
      @NonNull ConstraintLayout container, @Nullable ProgressBar loading,
      @NonNull EditText password, @Nullable Button runMiner, @Nullable Button startButton,
      @Nullable TextView textView, @NonNull EditText worker) {
    this.rootView = rootView;
    this.ServerHost = ServerHost;
    this.container = container;
    this.loading = loading;
    this.password = password;
    this.runMiner = runMiner;
    this.startButton = startButton;
    this.textView = textView;
    this.worker = worker;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ServerHost;
      EditText ServerHost = ViewBindings.findChildViewById(rootView, id);

      ConstraintLayout container = (ConstraintLayout) rootView;

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);

      id = R.id.password;
      EditText password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.runMiner;
      Button runMiner = ViewBindings.findChildViewById(rootView, id);

      id = R.id.startButton;
      Button startButton = ViewBindings.findChildViewById(rootView, id);

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);

      id = R.id.worker;
      EditText worker = ViewBindings.findChildViewById(rootView, id);
      if (worker == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, ServerHost, container, loading,
          password, runMiner, startButton, textView, worker);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
