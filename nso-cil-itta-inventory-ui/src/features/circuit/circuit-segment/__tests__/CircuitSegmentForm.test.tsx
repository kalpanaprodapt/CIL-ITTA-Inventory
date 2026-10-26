import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { describe, expect, it, vi } from 'vitest';
import { CircuitSegmentForm } from '../CircuitSegmentForm';
import { circuitSegmentFixture } from './fixtures';

describe('CircuitSegmentForm', () => {
  it('renders every field', () => {
    render(<CircuitSegmentForm onSubmit={vi.fn()} />);
    expect(screen.getByRole('form', { name: 'Circuit Segment form' })).toBeInTheDocument();
  });

  it('shows validation errors and blocks submit when empty', async () => {
    const onSubmit = vi.fn();
    render(<CircuitSegmentForm onSubmit={onSubmit} />);
    fireEvent.click(screen.getByRole('button', { name: 'Save' }));
    await waitFor(() => expect(screen.getAllByText(/is required/).length).toBeGreaterThan(0));
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('submits cleaned values for an existing record', async () => {
    const onSubmit = vi.fn();
    render(<CircuitSegmentForm initial={circuitSegmentFixture} onSubmit={onSubmit} submitLabel="Save changes" />);
    fireEvent.click(screen.getByRole('button', { name: 'Save changes' }));
    await waitFor(() => expect(onSubmit).toHaveBeenCalledTimes(1));
    expect(onSubmit.mock.calls[0][0].code).toBe(circuitSegmentFixture.code);
  });

  it('disables the submit button while busy', () => {
    render(<CircuitSegmentForm onSubmit={vi.fn()} busy />);
    expect(screen.getByRole('button', { name: /Saving/ })).toBeDisabled();
  });

  it('shows the server error', () => {
    render(<CircuitSegmentForm onSubmit={vi.fn()} serverError="Duplicate code" />);
    expect(screen.getByRole('alert')).toHaveTextContent('Duplicate code');
  });

  it('calls onCancel', () => {
    const onCancel = vi.fn();
    render(<CircuitSegmentForm onSubmit={vi.fn()} onCancel={onCancel} />);
    fireEvent.click(screen.getByRole('button', { name: 'Cancel' }));
    expect(onCancel).toHaveBeenCalled();
  });
});
