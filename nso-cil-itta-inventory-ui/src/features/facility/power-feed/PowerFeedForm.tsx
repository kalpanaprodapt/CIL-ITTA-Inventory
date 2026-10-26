import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { POWER_FEED_FEED_TYPE_OPTIONS, POWER_FEED_CRITICALITY_OPTIONS, PowerFeedFeedType, PowerFeedCriticality } from './types';
import { validatePowerFeed, cleanPowerFeedValues } from './schema';
import { POWERFEED_DEFAULTS } from './types';
import type { PowerFeedFormValues, PowerFeedInput } from './types';

interface Props {
  initial?: PowerFeedFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PowerFeedInput) => void;
  onCancel?: () => void;
}

export function PowerFeedForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PowerFeedFormValues>({ ...POWERFEED_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PowerFeedFormValues>(key: K, value: PowerFeedFormValues[K]) {
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
    const result = validatePowerFeed(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPowerFeedValues(values as Record<string, unknown>) as unknown as PowerFeedInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Power Feed form">
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
      <FormField label="Room Id" htmlFor="roomId" error={errors.roomId} required={true}>
        <Input
          id="roomId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.roomId ?? ''}
          onChange={(v) => set('roomId', v)}
        />
      </FormField>
      <FormField label="Feed Type" htmlFor="feedType" error={errors.feedType} required={false}>
        <Select
          id="feedType"
          value={values.feedType ?? ''}
          options={POWER_FEED_FEED_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('feedType', (v || undefined) as PowerFeedFeedType | undefined)}
        />
      </FormField>
      <FormField label="Voltage" htmlFor="voltage" error={errors.voltage} required={false}>
        <NumberInput
          id="voltage"
          value={values.voltage}
          step={0.0001}
          onChange={(v) => set('voltage', v)}
        />
      </FormField>
      <FormField label="Amperage" htmlFor="amperage" error={errors.amperage} required={false}>
        <NumberInput
          id="amperage"
          value={values.amperage}
          step={0.0001}
          onChange={(v) => set('amperage', v)}
        />
      </FormField>
      <FormField label="Phase" htmlFor="phase" error={errors.phase} required={false}>
        <Input
          id="phase"
          value={values.phase ?? ''}
          maxLength={255}
          onChange={(v) => set('phase', v)}
        />
      </FormField>
      <FormField label="Breaker Label" htmlFor="breakerLabel" error={errors.breakerLabel} required={false}>
        <Input
          id="breakerLabel"
          value={values.breakerLabel ?? ''}
          maxLength={255}
          onChange={(v) => set('breakerLabel', v)}
        />
      </FormField>
      <FormField label="Redundancy Group" htmlFor="redundancyGroup" error={errors.redundancyGroup} required={false}>
        <Input
          id="redundancyGroup"
          value={values.redundancyGroup ?? ''}
          maxLength={255}
          onChange={(v) => set('redundancyGroup', v)}
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
          options={POWER_FEED_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as PowerFeedCriticality | undefined)}
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
