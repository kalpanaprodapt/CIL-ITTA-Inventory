import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { TICKET_LINK_TICKET_SYSTEM_OPTIONS, TICKET_LINK_CRITICALITY_OPTIONS, TicketLinkTicketSystem, TicketLinkCriticality } from './types';
import { validateTicketLink, cleanTicketLinkValues } from './schema';
import { TICKETLINK_DEFAULTS } from './types';
import type { TicketLinkFormValues, TicketLinkInput } from './types';

interface Props {
  initial?: TicketLinkFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: TicketLinkInput) => void;
  onCancel?: () => void;
}

export function TicketLinkForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<TicketLinkFormValues>({ ...TICKETLINK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof TicketLinkFormValues>(key: K, value: TicketLinkFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateTicketLink(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanTicketLinkValues(values as Record<string, unknown>) as unknown as TicketLinkInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Ticket Link form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Alarm Id" htmlFor="alarmId" error={errors.alarmId} required={true}>
        <Input
          id="alarmId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.alarmId ?? ''}
          onChange={(v) => set('alarmId', v)}
        />
      </FormField>
      <FormField label="Ticket System" htmlFor="ticketSystem" error={errors.ticketSystem} required={false}>
        <Select
          id="ticketSystem"
          value={values.ticketSystem ?? ''}
          options={TICKET_LINK_TICKET_SYSTEM_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('ticketSystem', (v || undefined) as TicketLinkTicketSystem | undefined)}
        />
      </FormField>
      <FormField label="Ticket Number" htmlFor="ticketNumber" error={errors.ticketNumber} required={true}>
        <Input
          id="ticketNumber"
          value={values.ticketNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('ticketNumber', v)}
        />
      </FormField>
      <FormField label="Linked At" htmlFor="linkedAt" error={errors.linkedAt} required={false}>
        <DateTimeInput
          id="linkedAt"
          value={values.linkedAt ?? ''}
          onChange={(v) => set('linkedAt', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={TICKET_LINK_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as TicketLinkCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
