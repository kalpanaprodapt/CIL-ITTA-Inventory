import { expect, test } from '@playwright/test';

test.describe('Alarms and Events', () => {
  test('Alarm Definition list renders', async ({ page }) => {
    await page.goto('/alarm/alarm-definitions');
    await expect(page.getByRole('heading', { name: 'Alarm Definitions' }).or(page.getByRole('heading', { name: 'Alarm Definitiones' }))).toBeVisible();
  });

  test('Alarm Definition create form is reachable', async ({ page }) => {
    await page.goto('/alarm/alarm-definitions/new');
    await expect(page.getByRole('form', { name: 'Alarm Definition form' })).toBeVisible();
  });

  test('Alarm Instance list renders', async ({ page }) => {
    await page.goto('/alarm/alarm-instances');
    await expect(page.getByRole('heading', { name: 'Alarm Instances' }).or(page.getByRole('heading', { name: 'Alarm Instancees' }))).toBeVisible();
  });

  test('Alarm Instance create form is reachable', async ({ page }) => {
    await page.goto('/alarm/alarm-instances/new');
    await expect(page.getByRole('form', { name: 'Alarm Instance form' })).toBeVisible();
  });

  test('Event Source list renders', async ({ page }) => {
    await page.goto('/alarm/event-sources');
    await expect(page.getByRole('heading', { name: 'Event Sources' }).or(page.getByRole('heading', { name: 'Event Sourcees' }))).toBeVisible();
  });

  test('Event Source create form is reachable', async ({ page }) => {
    await page.goto('/alarm/event-sources/new');
    await expect(page.getByRole('form', { name: 'Event Source form' })).toBeVisible();
  });

  test('Suppression Rule list renders', async ({ page }) => {
    await page.goto('/alarm/suppression-rules');
    await expect(page.getByRole('heading', { name: 'Suppression Rules' }).or(page.getByRole('heading', { name: 'Suppression Rulees' }))).toBeVisible();
  });

  test('Suppression Rule create form is reachable', async ({ page }) => {
    await page.goto('/alarm/suppression-rules/new');
    await expect(page.getByRole('form', { name: 'Suppression Rule form' })).toBeVisible();
  });

  test('Ticket Link list renders', async ({ page }) => {
    await page.goto('/alarm/ticket-links');
    await expect(page.getByRole('heading', { name: 'Ticket Links' }).or(page.getByRole('heading', { name: 'Ticket Linkes' }))).toBeVisible();
  });

  test('Ticket Link create form is reachable', async ({ page }) => {
    await page.goto('/alarm/ticket-links/new');
    await expect(page.getByRole('form', { name: 'Ticket Link form' })).toBeVisible();
  });
});
