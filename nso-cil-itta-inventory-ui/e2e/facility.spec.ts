import { expect, test } from '@playwright/test';

test.describe('Facility', () => {
  test('Floor list renders', async ({ page }) => {
    await page.goto('/facility/floors');
    await expect(page.getByRole('heading', { name: 'Floors' }).or(page.getByRole('heading', { name: 'Floores' }))).toBeVisible();
  });

  test('Floor create form is reachable', async ({ page }) => {
    await page.goto('/facility/floors/new');
    await expect(page.getByRole('form', { name: 'Floor form' })).toBeVisible();
  });

  test('Room list renders', async ({ page }) => {
    await page.goto('/facility/rooms');
    await expect(page.getByRole('heading', { name: 'Rooms' }).or(page.getByRole('heading', { name: 'Roomes' }))).toBeVisible();
  });

  test('Room create form is reachable', async ({ page }) => {
    await page.goto('/facility/rooms/new');
    await expect(page.getByRole('form', { name: 'Room form' })).toBeVisible();
  });

  test('Rack list renders', async ({ page }) => {
    await page.goto('/facility/racks');
    await expect(page.getByRole('heading', { name: 'Racks' }).or(page.getByRole('heading', { name: 'Rackes' }))).toBeVisible();
  });

  test('Rack create form is reachable', async ({ page }) => {
    await page.goto('/facility/racks/new');
    await expect(page.getByRole('form', { name: 'Rack form' })).toBeVisible();
  });

  test('Rack Unit list renders', async ({ page }) => {
    await page.goto('/facility/rack-units');
    await expect(page.getByRole('heading', { name: 'Rack Units' }).or(page.getByRole('heading', { name: 'Rack Unites' }))).toBeVisible();
  });

  test('Rack Unit create form is reachable', async ({ page }) => {
    await page.goto('/facility/rack-units/new');
    await expect(page.getByRole('form', { name: 'Rack Unit form' })).toBeVisible();
  });

  test('Power Feed list renders', async ({ page }) => {
    await page.goto('/facility/power-feeds');
    await expect(page.getByRole('heading', { name: 'Power Feeds' }).or(page.getByRole('heading', { name: 'Power Feedes' }))).toBeVisible();
  });

  test('Power Feed create form is reachable', async ({ page }) => {
    await page.goto('/facility/power-feeds/new');
    await expect(page.getByRole('form', { name: 'Power Feed form' })).toBeVisible();
  });
});
