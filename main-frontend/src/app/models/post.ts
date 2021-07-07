export class Post {

  title: string;
  content: string;
  username: string;
  createOn: string;
  updateOn: string;

  constructor(title: string, content: string, username: string, createOn: string, updateOn: string) {
    this.title = title;
    this.content = content;
    this.username = username;
    this.createOn = createOn;
    this.updateOn = updateOn;
  }
}