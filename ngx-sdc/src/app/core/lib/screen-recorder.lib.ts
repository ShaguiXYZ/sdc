class SdcScreenRecorder {
  private static displayMediaOptions: DisplayMediaStreamOptions = {
    video: {
      frameRate: { ideal: 60 },
      displaySurface: 'browser'
    },
    sysAudio: false,
    preferCurrentTab: true,
    selfBrowserSurface: 'include',
    surfaceSwitching: 'include',
    monitorTypeSurfaces: 'include'
  } as DisplayMediaStreamOptions;

  public static startRecording = async (options: DisplayMediaStreamOptions = this.displayMediaOptions) => {
    const mediaOptions = { ...this.displayMediaOptions, ...options };

    const media = await navigator.mediaDevices.getDisplayMedia(mediaOptions);
    const mediarecorder = new MediaRecorder(media, {
      mimeType: 'video/webm;codecs=vp8,opus'
    });
    const [video] = media.getVideoTracks();

    mediarecorder.start();

    video.addEventListener('ended', () => {
      mediarecorder.stop();
    });

    mediarecorder.addEventListener('dataavailable', e => {
      const link = document.createElement('a');
      link.href = URL.createObjectURL(e.data);
      link.download = 'captura.webm';
      link.click();
    });
  };
}

export default SdcScreenRecorder;
