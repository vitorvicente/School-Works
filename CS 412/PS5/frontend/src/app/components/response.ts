export class Response {

  constructor(
    public base_code: string,
    public conversion_rate: number,
    public documentation: string,
    public result: string,
    public target_code: string,
    public terms_of_use: string,
    public time_last_update_unix: number,
    public time_last_update_utc: string,
    public time_next_update_unix: number,
    public time_next_update_utc: string
  ) {  }

}
