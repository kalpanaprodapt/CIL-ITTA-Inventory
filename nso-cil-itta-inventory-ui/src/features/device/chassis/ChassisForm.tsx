import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CHASSIS_LIFECYCLE_STAGE_OPTIONS, CHASSIS_CRITICALITY_OPTIONS, ChassisLifecycleStage, ChassisCriticality } from './types';
import { validateChassis, cleanChassisValues } from './schema';
import { CHASSIS_DEFAULTS } from './types';
import type { ChassisFormValues, ChassisInput } from './types';

interface Props {
  initial?: ChassisFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ChassisInput) => void;
  onCancel?: () => void;
}

export function ChassisForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ChassisFormValues>({ ...CHASSIS_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ChassisFormValues>(key: K, value: ChassisFormValues[K]) {
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
    const result = validateChassis(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanChassisValues(values as Record<string, unknown>) as unknown as ChassisInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Chassis form">
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
      <FormField label="Device Id" htmlFor="deviceId" error={errors.deviceId} required={true}>
        <Input
          id="deviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.deviceId ?? ''}
          onChange={(v) => set('deviceId', v)}
        />
      </FormField>
      <FormField label="Slot Count" htmlFor="slotCount" error={errors.slotCount} required={true}>
        <NumberInput
          id="slotCount"
          value={values.slotCount}
          step={1}
          onChange={(v) => set('slotCount', v)}
        />
      </FormField>
      <FormField label="Serial Number" htmlFor="serialNumber" error={errors.serialNumber} required={false}>
        <Input
          id="serialNumber"
          value={values.serialNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('serialNumber', v)}
        />
      </FormField>
      <FormField label="Backplane Gbps" htmlFor="backplaneGbps" error={errors.backplaneGbps} required={false}>
        <NumberInput
          id="backplaneGbps"
          value={values.backplaneGbps}
          step={0.0001}
          onChange={(v) => set('backplaneGbps', v)}
        />
      </FormField>
      <FormField label="Redundancy Mode" htmlFor="redundancyMode" error={errors.redundancyMode} required={false}>
        <Input
          id="redundancyMode"
          value={values.redundancyMode ?? ''}
          maxLength={255}
          onChange={(v) => set('redundancyMode', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={CHASSIS_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ChassisLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
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
          options={CHASSIS_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ChassisCriticality | undefined)}
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
