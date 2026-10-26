import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { BGP_SESSION_SESSION_STATE_OPTIONS, BGP_SESSION_CRITICALITY_OPTIONS } from './types';
import type { BgpSessionFilter } from './types';

interface Props {
  value: BgpSessionFilter;
  onChange: (next: BgpSessionFilter) => void;
  onReset: () => void;
}

export function BgpSessionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: BgpSessionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Bgp Session filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Bgp Session"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Local Device Id">
        <Input
          aria-label="Local Device Id filter"
          placeholder="UUID"
          value={value.localDeviceId ?? ''}
          onChange={(v) => patch({ localDeviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Peer Address">
        <Input
          aria-label="Peer Address filter"
          value={value.peerAddress ?? ''}
          onChange={(v) => patch({ peerAddress: v || undefined })}
        />
      </FilterField>
      <FilterField label="Local Asn">
        <RangeInputs
          ariaLabel="Local Asn"
          min={value.localAsnMin}
          max={value.localAsnMax}
          onChange={(min, max) => patch({ localAsnMin: min, localAsnMax: max })}
        />
      </FilterField>
      <FilterField label="Remote Asn">
        <RangeInputs
          ariaLabel="Remote Asn"
          min={value.remoteAsnMin}
          max={value.remoteAsnMax}
          onChange={(min, max) => patch({ remoteAsnMin: min, remoteAsnMax: max })}
        />
      </FilterField>
      <FilterField label="Session State">
        <MultiSelect
          ariaLabel="Session State filter"
          options={BGP_SESSION_SESSION_STATE_OPTIONS}
          value={value.sessionState ?? []}
          onChange={(v) => patch({ sessionState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Prefix Limit">
        <RangeInputs
          ariaLabel="Prefix Limit"
          min={value.prefixLimitMin}
          max={value.prefixLimitMax}
          onChange={(min, max) => patch({ prefixLimitMin: min, prefixLimitMax: max })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={BGP_SESSION_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
