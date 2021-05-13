/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.camera.extensions.impl.advanced;

import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;

import java.util.List;
import java.util.Map;

/**
 * A Interface to execute requests.
 */
public interface RequestProcessorImpl {
    /**
     * Sets a {@link ImageProcessorImpl} to receive {@link ImageReferenceImpl} to process.
     */
    void setImageProcessor(int outputconfigId, ImageProcessorImpl imageProcessor);

    /**
     * Submits a request.
     */
    boolean submit(Request request, Callback callback);

    /**
     * Submits a list of requests.
     */
    boolean submit(List<Request> requests, Callback callback);

    /**
     * Set repeating requests.
     */
    boolean setRepeating(Request request, Callback callback);

    /**
     * Abort captures.
     */
    void abortCaptures();

    /**
     * Stop Repeating.
     */
    void stopRepeating();

    /**
     * A interface representing a capture request configuration used for submitting requests in
     * {@link RequestProcessorImpl}.
     */
    interface Request {
        /**
         * Gets the target ids of {@link Camera2OutputConfigImpl} which identifies corresponding
         * Surface to be the targeted for the request.
         */
        List<Integer> getTargetOutputConfigIds();

        /**
         * Gets all the parameters.
         */
        Map<CaptureRequest.Key<?>, Object> getParameters();

        /**
         * Gets the template id.
         */
        Integer getTemplateId();
    }

    /**
     * Callback to be invoked during the capture.
     */
    interface Callback {
        void onCaptureStarted(
                Request request,
                long frameNumber,
                long timestamp);

        void onCaptureProgressed(
                Request request,
                CaptureResult partialResult);

        void onCaptureCompleted(
                Request request,
                TotalCaptureResult totalCaptureResult);

        void onCaptureFailed(
                Request request,
                CaptureFailure captureFailure);

        void onCaptureBufferLost(
                Request request,
                long frameNumber,
                int outputStreamId);

        void onCaptureSequenceCompleted(int sequenceId, long frameNumber);

        void onCaptureSequenceAborted(int sequenceId);

    }
}
